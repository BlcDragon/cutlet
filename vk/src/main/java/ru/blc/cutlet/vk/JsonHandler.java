package ru.blc.cutlet.vk;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import ru.blc.cutlet.api.command.sender.CommandSender;
import ru.blc.cutlet.api.event.Event;
import ru.blc.cutlet.vk.event.chat.*;
import ru.blc.cutlet.vk.method.messages.SendMessageEventAnswer;
import ru.blc.cutlet.vk.objects.main.ChatAction;
import ru.blc.cutlet.vk.objects.main.Message;
import ru.blc.cutlet.vk.objects.main.keyboard.ButtonAction;
import ru.blc.objconfig.ConfigurationSection;
import ru.blc.objconfig.FileConfiguration;
import ru.blc.objconfig.json.JsonConfiguration;

public class JsonHandler {

    private final VkModule vkModule;

    public JsonHandler(VkModule vkModule){
        this.vkModule = vkModule;
    }

    public void handleJson(ConfigurationSection json, VkBot bot){
        Preconditions.checkNotNull(json, "json");
        Preconditions.checkNotNull(bot, "bot");
        if (!json.getString("secret", "").equalsIgnoreCase(bot.getSecret())) {
            if (bot.getLogger().isWarnEnabled()){
                FileConfiguration j;
                if (json instanceof FileConfiguration){
                    j = (FileConfiguration) json;
                }else {
                    j = FileConfiguration.createFromSection(JsonConfiguration.class, json);
                }
                bot.getLogger().warn("Received wrong secret key {}, json = {}", json.getString("secret", ""), j.saveToString());
            }
            return;
        }
        boolean result = false;
        String type = json.getString("type");
        vkModule.getLogger().debug("Message type is {}", type);
        if (type.equals("message_new")) {
            vkModule.getLogger().debug("new message caught");
            result = handleNewMessage(json, bot);
        }
        if (type.equals("message_event")) {
            vkModule.getLogger().debug("message event caught");
            result = handleMessageEvent(json, bot);
        }
        if (!result && vkModule.getLogger().isDebugEnabled()){
            FileConfiguration j;
            if (json instanceof FileConfiguration){
                j = (FileConfiguration) json;
            }else {
                j = FileConfiguration.createFromSection(JsonConfiguration.class, json);
            }
            vkModule.getLogger().debug("Json with type {} not handled. Json = {}", type, j.saveToString());
        }
    }

    /**
     * Обработчик входящих событий с маркером "message_new"
     * @param json json
     * @param bot бот
     * @return true, если событие обработано
     */
    protected boolean handleNewMessage(@NotNull ConfigurationSection json, @NotNull VkBot bot){
        int group = json.getInt("group_id");
        Message message = Message.load(json.getConfigurationSection("object.message"));
        if (message==null){
            FileConfiguration j;
            if (json instanceof FileConfiguration){
                j = (FileConfiguration) json;
            }else {
                j = FileConfiguration.createFromSection(JsonConfiguration.class, json);
            }
            vkModule.getLogger().error("Message json not parsed. Message not founded. json = {}", j.saveToString());
            return false;
        }
        if (message.getAction()!=null) {
            switch (message.getAction().getType()) {
                case CHAT_PIN_MESSAGE:{
                    ChatAction action = message.getAction();
                    int pinnedBy = action.getData().getInt("member_id");
                    int conversationMessageId = action.getData().getInt("conversation_message_id");
                    String text = action.getData().getString("message");
                    Event event= new MessagePinEvent(message, message.getAction(), pinnedBy, conversationMessageId, text);
                    vkModule.getCutlet().getBotManager().callEvent(event, b -> b instanceof VkBot && ((VkBot) b).getGroupId()==group);
                    return true;
                }
                case CHAT_INVITE_USER:{
                    ChatAction action = message.getAction();
                    int joinerId = message.getAction().getData().getInt("member_id");
                    int inviterId = message.getFromId();
                    UserJoinChatEvent.Reason reason = null;
                    if (joinerId==inviterId) {
                        reason = UserJoinChatEvent.Reason.RETURN;
                    }else {
                        reason = UserJoinChatEvent.Reason.INVITED_BY_OTHER;
                    }
                    Event event = new UserJoinChatEvent(message, action, joinerId, reason);
                    vkModule.getCutlet().getBotManager().callEvent(event, b -> b instanceof VkBot && ((VkBot) b).getGroupId()==group);
                    return true;
                }
                case CHAT_KICK_USER:{
                    ChatAction action = message.getAction();
                    int quiterId = message.getAction().getData().getInt("member_id");
                    int kickerId = message.getFromId();
                    UserQuitChatEvent.Reason reason = null;
                    if (quiterId==kickerId) {
                        reason = UserQuitChatEvent.Reason.LEAVE;
                    }else {
                        reason = UserQuitChatEvent.Reason.KICKED;
                    }
                    Event event = new UserQuitChatEvent(message, action, quiterId, reason);
                    vkModule.getCutlet().getBotManager().callEvent(event, b -> b instanceof VkBot && ((VkBot) b).getGroupId()==group);
                    return true;
                }
                case CHAT_INVITE_USER_BY_LINK:{
                    Event event = new UserJoinChatEvent(message, message.getAction(), 0, UserJoinChatEvent.Reason.LINK_JOIN);
                    vkModule.getCutlet().getBotManager().callEvent(event, b -> b instanceof VkBot && ((VkBot) b).getGroupId()==group);
                    return true;
                }
                case CHAT_TITLE_UPDATE: {
                    int updatedBy = message.getFromId();
                    String text = message.getAction().getData().getString("text");
                    Event event = new ChatTitleUpdateEvent(message, message.getAction(), updatedBy, text);
                    vkModule.getCutlet().getBotManager().callEvent(event, b -> b instanceof VkBot && ((VkBot) b).getGroupId()==group);
                    return true;
                }
                default:
                    if (vkModule.getLogger().isDebugEnabled()){
                        FileConfiguration j;
                        if (json instanceof FileConfiguration){
                            j = (FileConfiguration) json;
                        }else {
                            j = FileConfiguration.createFromSection(JsonConfiguration.class, json);
                        }
                        vkModule.getLogger().debug("message action {} not parsed at json {}", message.getAction().getType(), j.saveToString());
                    }
                    return false;
            }
        }
        String msg = message.getText();
        if (msg!=null && !msg.isEmpty() && msg.startsWith(bot.getCommandsPrefix())
                && message.getDate()>=System.currentTimeMillis()/1000 - 10){
            vkModule.getLogger().debug("Command {} in message", msg);
            CommandSender sender = bot.getCommandSender(message);
            if (vkModule.getCutlet().dispatchCommand(msg.replaceFirst(bot.getCommandsPrefix(), ""), sender)){
                if (sender.isDeleteIfPM()){
                    VkModule.METHODS
                            .messages
                            .delete
                            .getNewParamsSet()
                            .setMessageIds(message.getId())
                            .setToken(bot.getDefaultToken())
                            .call();
                }
                return true;
            }
        }
        vkModule.getLogger().debug("Command not dispatched, message event");

        if (json.hasValue("object.message.payload")){
            ConfigurationSection payloadJ = message.getPayload();
            if (payloadJ==null){
                FileConfiguration j;
                if (json instanceof FileConfiguration){
                    j = (FileConfiguration) json;
                }else {
                    j = FileConfiguration.createFromSection(JsonConfiguration.class, json);
                }
                vkModule.getLogger().error("Received button press message, but payload is null. json = {}", j.saveToString());
                return true;
            }
            if (!payloadJ.hasValue("id")){
                //не наша кнопка
                return true;
            }
            vkModule.getLogger().debug("detected button press");
            long id = payloadJ.getLong("id");
            ButtonAction a = ButtonAction.getAction(id);
            if (a==null){
                vkModule.getLogger().debug("Button with id {} not founded", id);
                return true;
            }
            a.onPress(bot.getCommandSender(message), vkModule.getLogger());
            return true;
        }

        MessageNewEvent event = new MessageNewEvent(message, bot.getCommandSender(message));
        vkModule.getCutlet().getBotManager().callEvent(event, b -> b instanceof VkBot && ((VkBot) b).getGroupId()==group);
        return true;
    }

    protected boolean handleMessageEvent(@NotNull ConfigurationSection json, @NotNull VkBot bot){
        ConfigurationSection event = json.getConfigurationSection("object");
        JsonConfiguration j = new JsonConfiguration();
        j.set("conversation_message_id", event.getInt("conversation_message_id",0));
        j.set("from_id",event.getInt("user_id"));
        j.set("peer_id",event.getInt("peer_id"));
        String eventId = event.getString("event_id");
        if (eventId==null||eventId.isEmpty()){
            vkModule.getLogger().error("Received callback button press without event id");
            return true;
        }
        j.set("text", eventId);
        j.set("payload",event.getConfigurationSection("payload"));
        SendMessageEventAnswer.notAnsweredData.add(eventId);
        Message message = Message.load(j);
        ConfigurationSection payloadJ = message.getPayload();
        if (payloadJ==null){
            FileConfiguration j1;
            if (json instanceof FileConfiguration){
                j1 = (FileConfiguration) json;
            }else {
                j1 = FileConfiguration.createFromSection(JsonConfiguration.class, json);
            }
            vkModule.getLogger().error("Received button press message, but payload is null. json = {}", j1.saveToString());
            answerButtonEventEmpty(message, bot);
            return true;
        }
        long id = payloadJ.getLong("id");
        ButtonAction a = ButtonAction.getAction(id);
        if (a==null){
            vkModule.getLogger().debug("Button with id {} not founded", id);
            answerButtonEventEmpty(message, bot);
            return true;
        }
        a.onPress(bot.getCommandSender(message), vkModule.getLogger());
        if (SendMessageEventAnswer.notAnsweredData.contains(eventId)){
            answerButtonEventEmpty(message, bot);
        }
        return true;
    }

    protected void answerButtonEventEmpty(Message event, VkBot bot){
        SendMessageEventAnswer.notAnsweredData.remove(event.getText());
        VkModule.METHODS
                .messages
                .sendMessageEventAnswer
                .getNewParamsSet()
                .setEventId(event.getText())
                .setPeerId(event.getPeerId())
                .setUserId(event.getFromId())
                .setToken(bot.getDefaultToken())
                .call();
    }
}
