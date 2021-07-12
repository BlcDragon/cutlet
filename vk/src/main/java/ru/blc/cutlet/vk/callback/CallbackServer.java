package ru.blc.cutlet.vk.callback;

import com.google.common.base.Preconditions;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;
import ru.blc.cutlet.vk.JsonHandler;
import ru.blc.cutlet.vk.VkBot;
import ru.blc.cutlet.vk.VkModule;
import ru.blc.objconfig.json.JsonConfiguration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CallbackServer {

    private static AtomicBoolean created = new AtomicBoolean(false);

    private final VkModule vkModule;
    private HttpServer server;
    private final String callbackAddress;

    public CallbackServer(VkModule vkModule, String callbackAddress, String host, int port) {
        Preconditions.checkNotNull(vkModule, "Vk module");
        Preconditions.checkArgument(created.compareAndSet(false, true), "Callback server already started");
        this.vkModule = vkModule;
        if (callbackAddress==null) callbackAddress="";
        this.callbackAddress = callbackAddress.startsWith("/")?callbackAddress:"/"+callbackAddress;
        if (host==null) host = "0.0.0.0";
        final ServerBootstrap bootstrap;
        try {
            bootstrap = ServerBootstrap.bootstrap()
                    .registerHandler(this.callbackAddress +"*", new LoggingHandler())
                    .setLocalAddress(InetAddress.getByName(host))
                    .setListenerPort(port);
        } catch (UnknownHostException e1) {
           vkModule.getLogger().error("Could not create callback server", e1);
           return;
        }

        try {
            server = bootstrap.create();
            server.start();
            vkModule.getLogger().info("Server started at {}:{}{}", server.getInetAddress().getHostAddress(), server.getLocalPort(),getCallbackAddress());
        } catch (IOException e) {
            vkModule.getLogger().error("Error while starting Callback Server (CS), attempt 1/10, i'll try start it 10 seconds later", e);
            Thread t = new Thread() {
                final AtomicInteger attempts = new AtomicInteger(1);
                final AtomicBoolean success = new AtomicBoolean(false);
                @Override
                public void run() {
                    while (attempts.get()<=10){
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e1) {
                            vkModule.getLogger().error("Error with sleeping WTF 0_0", e1);
                        }
                        try {
                            server = bootstrap.create();
                            server.start();
                            vkModule.getLogger().info("Server started at {}:{}{}", server.getInetAddress().getHostAddress(), server.getLocalPort(),getCallbackAddress());
                            break;
                        } catch (IOException ioException) {
                            vkModule.getLogger().error("Error while starting CS, attempt {}/10, i'll try start it 10 seconds later", attempts.addAndGet(1));
                        }
                    }
                    if (!success.get()){
                        vkModule.getLogger().error("CALLBACK SERVER NOT STARTED, CALLBACK API WOULD NOT WORK");
                    }
                }
            };
            t.setName("CS starter");
            t.setDaemon(true);
            t.start();
        }
    }

    public String getCallbackAddress() {
        return callbackAddress;
    }

    public void stop() {
        try {
            if (server!=null) server.shutdown(0L, null);
        }catch (Exception ignore){}
        vkModule.getLogger().info("Callback server stopped");
    }

    public class LoggingHandler implements HttpRequestHandler {

        public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws IOException {
            HttpEntity entity = null;
            if (httpRequest instanceof HttpEntityEnclosingRequest)
                entity = ((HttpEntityEnclosingRequest)httpRequest).getEntity();

            byte[] data;
            if (entity == null) {
                data = new byte [0];
            } else {
                data = EntityUtils.toByteArray(entity);
            }
            int bytesl = data.length;
            if (bytesl>0) {
                String urlDecodedInput = new String(data, StandardCharsets.UTF_8);
                vkModule.getLogger().debug("Received message {}", urlDecodedInput);
                JsonConfiguration json;
                try {
                    json =JsonConfiguration.loadConfiguration(urlDecodedInput);
                    if (!json.hasValue("group_id")) {
                        vkModule.getLogger().error("Received json without group id. json = {}", urlDecodedInput);
                        return;
                    }
                    int group = json.getInt("group_id");
                    VkBot bot = vkModule.getCallbackBot(group);
                    if (bot==null){
                        vkModule.getLogger().error("Received json from group {}, but bot not founded. json = {}", group, urlDecodedInput);
                        return;
                    }
                    if (!json.hasValue("type")){
                        vkModule.getLogger().error("Received json without type from group {}. json = {}", group, urlDecodedInput);
                        return;
                    }
                    if (!json.hasValue("secret")){
                        vkModule.getLogger().error("Received json without secret from group {}. json = {}", group, urlDecodedInput);
                        return;
                    }
                    if (json.getString("type").equalsIgnoreCase("confirmation")){
                        vkModule.getLogger().debug("Received confirmation for bot {}. Answer will {}", bot.getName(), bot.getConfirmation());
                        set200Result(httpResponse, bot.getConfirmation());
                        return;
                    }
                    set200Result(httpResponse, "ok");
                    try {
                        vkModule.getLogger().debug("Handling message");
                        JsonHandler jsonHandler = bot.getJsonHandler();
                        if (jsonHandler==null) jsonHandler = vkModule.getJsonHandler();
                        jsonHandler.handleJson(json, bot);
                    }catch(Exception e) {
                        vkModule.getLogger().error("Error while handling json. Json was "+urlDecodedInput, e);
                    }
                }catch(Exception e) {
                    set200Result(httpResponse, "ok");
                    vkModule.getLogger().error("Error while parsing json. Input was "+urlDecodedInput, e);
                }
            }
        }

        private void set200Result(HttpResponse httpResponse, String result) throws IOException {
            httpResponse.setStatusCode(200);
            httpResponse.setEntity(new StringEntity(result));
        }
    }
}
