package ru.blc.cutlet.vk;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Nullable;
import ru.blc.cutlet.api.command.Messenger;
import ru.blc.cutlet.api.module.Module;
import ru.blc.cutlet.vk.callback.CallbackServer;
import ru.blc.cutlet.vk.command.console.SetConfirmCommand;
import ru.blc.cutlet.vk.longpoll.LongPollConnection;
import ru.blc.cutlet.vk.method.Methods;

import java.util.HashMap;
import java.util.Map;

public class VkModule extends Module {
    public static final int CONVERSATIONS_IDS_ADD = 2000000000;
    public static final Methods METHODS = new Methods();
    public static final Messenger VK_MESSENGER = new Messenger() {};

    @Nullable private CallbackServer server;
    private JsonHandler jsonHandler;

    private final Map<Integer, VkBot> callbackBots = new HashMap<>();

    @Override
    public void onLoad() {
        saveDefaultConfig();
        reloadConfig();
    }

    @Override
    public void onEnable() {
        this.jsonHandler = new JsonHandler(this);
        if (getConfig().getBoolean("server.enabled")){
            server = new CallbackServer(this,
                    getConfig().getString("server.address", ""),
                    getConfig().getString("server.ip", "0.0.0.0"),
                    getConfig().getInt("server.port", 80));
        }
        getCutlet().getBotManager().registerCommand(null, new SetConfirmCommand());
    }

    @Override
    public void onDisable() {
        if (getServer()!=null){
            getServer().stop();
        }
    }

    /**
     * @return Сервер для CallBack api, или null
     */
    @Nullable
    public CallbackServer getServer() {
        return server;
    }

    /**
     * Отвечает за обработку всех входящих сообщений
     * @return JsonHandler
     */
    public JsonHandler getJsonHandler() {
        return jsonHandler;
    }

    /**
     * Устанавливает кастомный обработчик json
     * @param jsonHandler обработчик json событий
     * @throws NullPointerException если jsonHandler == null
     */
    public void setJsonHandler(JsonHandler jsonHandler) {
        Preconditions.checkNotNull(jsonHandler, "jsonHandler");
        this.jsonHandler = jsonHandler;
    }

    /**
     * Подключает бота к сервисам вк.
     * @param bot бот
     * @param longPoll true для longPollApi,
     */
    public void connectBot(VkBot bot, boolean longPoll){
        Preconditions.checkNotNull(bot, "bot");
        Preconditions.checkArgument(bot.getGroupId()>0, "group id should be more than zero");
        if (getCallbackBot(bot.getGroupId())!=null){
            getLogger().debug("Bot for group {} already connected. You do not have to register many bots just setup listeners", bot.getGroupId());
            return;
        }
        if (longPoll){
            LongPollConnection.getConnection(bot);
            return;
        }
        if (getServer()==null){
            getLogger().warn("Callback server disabled by configuration");
        }
        callbackBots.put(bot.getGroupId(), bot);
    }

    /**
     * Отключает теуцщего бота от связи с вк апи
     * @param bot бот
     */
    public void disconnectBot(VkBot bot){
        callbackBots.remove(bot.getGroupId());
        LongPollConnection.disconnect(bot);
    }

    /**
     * Возвращает вк бота для этой группы
     * @param groupId группа
     * @return бота или null
     */
    public VkBot getCallbackBot(int groupId){
        return callbackBots.get(groupId);
    }
}
