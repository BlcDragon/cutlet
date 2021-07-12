package ru.blc.cutlet.vk.longpoll;

import com.google.common.base.Preconditions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.cutlet.vk.JsonHandler;
import ru.blc.cutlet.vk.VkBot;
import ru.blc.cutlet.vk.VkModule;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.json.JsonConfiguration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

//TODO два потока. Один сканит обновления и добавляет их в очередь, второй обрабатывает очередь
public class LongPollConnection {
    /**
     * Возвращает текущее LongPoll подключение для указанного бота
     * или создает новое, если ни одного нет
     * @param bot бот
     * @return соединение
     */
    public static LongPollConnection getConnection(@NotNull VkBot bot){
        LongPollConnection connection = CONNECTIONS_BY_BOT.get(bot);
        if (connection==null){
            connection = new LongPollConnection(bot);
        }
        return connection;
    }

    /**
     * Прекращает текущее LongPoll подключение для этого бота
     * @param bot бот
     * @return true, если подключение существовало, false если бот не был подключен к LongPoll
     */
    public static boolean disconnect(@NotNull VkBot bot){
        LongPollConnection connection = CONNECTIONS_BY_BOT.remove(bot);
        if (connection==null){
            return false;
        }
        connection.stop();
        return true;
    }

    private static final Map<VkBot, LongPollConnection> CONNECTIONS_BY_BOT = new HashMap<>();
    private static final VkModule vkModule = Cutlet.instance().getModule(VkModule.class);

    private final VkBot bot;
    private boolean valid = false, running;

    private String key, server;
    private String ts;

    protected LongPollConnection(VkBot bot) {
        this.bot = bot;
        CONNECTIONS_BY_BOT.put(bot, this);
        this.running = true;
        getConnectionData().join();
        if (!isValid()){
            bot.getLogger().error("Long Poll connection for bot {} not created!", bot.getName());
            return;
        }
        Thread longPollRunner = new LongPollTask();
        longPollRunner.setDaemon(true);
        longPollRunner.setName(bot.getName()+" LP task");
        longPollRunner.start();
    }

    public VkBot getBot() {
        return bot;
    }

    /**
     * @return true, если соединение рабочее. в противном случае false
     */
    public boolean isValid() {
        return running&&valid;
    }

    public void stop(){
        this.running = false;
    }

    protected CompletableFuture<String> getConnectionData(){
        return VkModule.METHODS
                .groups
                .getLongPollServer
                .getNewParamsSet()
                .setGroupId(bot.getGroupId())
                .setToken(bot.getDefaultToken())
                .call()
                .whenComplete((s, t)->{
                    if (t!=null){
                        bot.getLogger().error("Error while creating long poll connection for bot "+bot.getName(), t);
                    }else {
                        JsonConfiguration json = JsonConfiguration.loadConfiguration(s);
                        if (json.hasValue("error")){
                            bot.getLogger().error("Error {} while creating long poll connection for bot {}. Message: {}",
                                    json.get("error.error_code"), bot.getName(), json.get("error.error_msg"));
                            return;
                        }
                        key = json.getString("response.key");
                        server = json.getString("response.server");
                        ts = json.getString("response.ts");
                        valid = true;
                    }
                });
    }

    protected String getUpdates() {
        Preconditions.checkState(valid, "Connection not valid!");
        String adr = server;
        try {
            RequestConfig globalConfig = RequestConfig.custom()
                    .setCookieSpec(CookieSpecs.DEFAULT)
                    .build();
            HttpClient httpclient = HttpClients.custom()
                    .setDefaultRequestConfig(globalConfig)
                    .build();
            RequestConfig localConfig = RequestConfig.copy(globalConfig)
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .build();
            HttpPost httppost = new HttpPost(adr);
            httppost.setConfig(localConfig);
            // Request parameters and other properties.
            List<NameValuePair> pars = new ArrayList<>();
            pars.add(new BasicNameValuePair("act", "a_check"));
            pars.add(new BasicNameValuePair("key", key));
            pars.add(new BasicNameValuePair("ts", String.valueOf(ts)));
            pars.add(new BasicNameValuePair("wait", "25"));
            httppost.setEntity(new UrlEncodedFormEntity(pars, "UTF-8"));


            // Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            byte[] data;
            if (entity == null) {
                data = new byte[0];
            } else {
                data = EntityUtils.toByteArray(entity);
            }
            int bytesl = data.length;
            if (bytesl>0) {
                return new String(data, StandardCharsets.UTF_8);
            }else {
                return null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            bot.getLogger().error("can't get updates for bot "+bot.getName(), ex);
        }
        return null;
    }

    protected class LongPollTask extends Thread{

        @Override
        public void run() {
            while (running&&isValid()){
                vkModule.getLogger().debug("Getting update, Bot {}", bot.getName());
                String s = getUpdates();
                if (s==null) {
                    vkModule.getLogger().debug("Null update, Bot {}", bot.getName());
                    continue;
                }
                JsonConfiguration updates = JsonConfiguration.loadConfiguration(s);
                if (updates.hasValue("failed")){
                    vkModule.getLogger().debug("Fail {} on update. Bot {}", updates.getInt("failed"), bot.getName());
                    switch (updates.getInt("failed")){
                        case 1: {
                            ts = updates.getString("ts");
                            continue;
                        }
                        case 2:
                        case 3:
                        default:
                            valid = false;
                            getConnectionData().join();
                            continue;
                    }
                }
                vkModule.getLogger().debug("Normal update got. Bot {}", bot.getName());
                ts = updates.getString("ts");
                int handled = 0;
                for (ConfigurationSection update : updates.getConfigurationSectionList("updates")) {
                    update.set("secret", bot.getSecret());
                    JsonHandler jsonHandler = bot.getJsonHandler();
                    if (jsonHandler==null) jsonHandler = vkModule.getJsonHandler();
                    try {
                        jsonHandler.handleJson(update, bot);
                    }catch (Exception e){
                        vkModule.getLogger().error("Failed handling Json", e);
                    }
                    handled++;
                }
                vkModule.getLogger().debug("Handled {} update(s). Bot {}", handled, bot.getName());
            }
            vkModule.getLogger().debug("Connection stopped. Bot {}", bot.getName());
            if (!valid){
                vkModule.getLogger().error("Connection for bot {} was failed! Automatic repair also failed!", bot.getName());
            }
        }
    }
}
