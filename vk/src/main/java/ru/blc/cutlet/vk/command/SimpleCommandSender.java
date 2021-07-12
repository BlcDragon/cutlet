package ru.blc.cutlet.vk.command;

import lombok.Getter;
import lombok.Setter;
import ru.blc.cutlet.api.bot.Bot;
import ru.blc.cutlet.api.command.Messenger;
import ru.blc.cutlet.api.command.sender.CommandSender;
import ru.blc.cutlet.api.command.sender.DialogType;
import ru.blc.cutlet.vk.VkBot;
import ru.blc.cutlet.vk.VkModule;
import ru.blc.cutlet.vk.method.messages.Send;
import ru.blc.cutlet.vk.objects.main.Message;
import ru.blc.objconfig.json.JsonConfiguration;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class SimpleCommandSender implements VkCommandSender {

    @Getter
    protected int peerId;
    @Getter
    protected int fromId;
    @Getter
    private final Message message;
    private final VkBot bot;
    @Getter
    private final DialogType dialogType;
    @Getter
    @Setter
    private boolean deleteIfPM;

    public SimpleCommandSender(Message message, VkBot bot){
        this.message = message;
        this.peerId = message.getPeerId();
        this.fromId = message.getFromId();
        this.bot = bot;
        this.deleteIfPM = true;
        if (peerId>VkModule.CONVERSATIONS_IDS_ADD){
            this.dialogType = DialogType.CONVERSATION;
        }else {
            this.dialogType = DialogType.PRIVATE_MESSAGE;
        }
    }

    @Override
    public boolean hasPermission(String permission) {
        bot.getLogger().warn("Did you does not override Simple Command Sender in your vk bot?");
        return false;
    }

    @Override
    public CommandSender getPmSender() {
        if (getFromId()==getPeerId()) return this;
        SimpleCommandSender cs = new SimpleCommandSender(message, bot);
        cs.peerId = cs.fromId;
        return cs;
    }

    @Override
    public String getName() {
        return "Vk simple sender: "+getFromId()+" at "+peerId;
    }

    @Override
    public void sendMessage(String message) {
        sendMessage0(message, isDeleteIfPM()&&getDialogType()==DialogType.PRIVATE_MESSAGE);
    }

    @Override
    public void sendAndDeleteMessage(String message) {
        sendMessage0(message, true);
    }

    @Override
    public void sendMessage(Object message) {
        sendMessage0(message, isDeleteIfPM()&&getDialogType()==DialogType.PRIVATE_MESSAGE);
    }

    @Override
    public void sendAndDeleteMessage(Object message) {
        sendMessage0(message, true);
    }

    private void sendMessage0(String message, boolean deleteAfter){
        bot.getLogger().debug("Sending message {} from bot {} for sender {}", message, bot.getName(), this);
        CompletableFuture<String> future = VkModule.METHODS
                .messages
                .send
                .getNewParamsSet()
                .setMessage(message)
                .setPeerId(getPeerId())
                .setToken(bot.getDefaultToken())
                .call();
        if (deleteAfter){
            future.whenComplete((s, t)->{
                if (t!=null){
                    bot.getLogger().error("Error while send and delete message ", t);
                    return;
                }
                bot.getLogger().debug("Answer for message is {}", s);
                int m = JsonConfiguration.loadConfiguration(s).getInt("response");
                if (m>0){
                    VkModule.METHODS
                            .messages
                            .delete
                            .getNewParamsSet()
                            .setMessageIds(m)
                            .setToken(bot.getDefaultToken())
                            .call();
                }
            });
        }
    }

    private void sendMessage0(Object message, boolean deleteAfter){
        if (message instanceof Send.SendParamsSet){
            CompletableFuture<String> future = ((Send.SendParamsSet) message)
                    .setPeerId(getPeerId())
                    .setToken(bot.getDefaultToken())
                    .call();
            if (deleteAfter) {
                future.whenComplete((s, t) -> {
                    if (t != null) {
                        bot.getLogger().error("Error while send and delete message ", t);
                        return;
                    }
                    int m = JsonConfiguration.loadConfiguration(s).getInt("response");
                    if (m > 0) {
                        VkModule.METHODS
                                .messages
                                .delete
                                .getNewParamsSet()
                                .setMessageIds(m)
                                .setToken(bot.getDefaultToken())
                                .call();
                    }
                });
            }
        }else {
            sendMessage0(String.valueOf(message), deleteAfter);
        }
    }

    @Override
    public Messenger getMessenger() {
        return VkModule.VK_MESSENGER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleCommandSender that = (SimpleCommandSender) o;
        return getPeerId() == that.getPeerId() && getFromId() == that.getFromId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPeerId(), getFromId());
    }

    @Override
    public Send.SendParamsSet getAnswerSet() {
        return (Send.SendParamsSet) VkModule.METHODS
                .messages
                .send
                .getNewParamsSet()
                .setPeerId(getPeerId())
                .setToken(bot.getDefaultToken());
    }

    @Override
    public Send.SendParamsSet getAnswerSet(String text) {
        return (Send.SendParamsSet) VkModule.METHODS
                .messages
                .send
                .getNewParamsSet()
                .setPeerId(getPeerId())
                .setMessage(text)
                .setToken(bot.getDefaultToken());
    }

    @Override
    public Bot getBot() {
        return (Bot) this.bot;
    }
}
