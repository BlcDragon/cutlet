package ru.blc.cutlet.vk.objects.main.keyboard;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.blc.cutlet.vk.VkBot;
import ru.blc.cutlet.vk.VkModule;
import ru.blc.cutlet.vk.command.VkCommandSender;
import ru.blc.cutlet.vk.method.messages.SendMessageEventAnswer;
import ru.blc.cutlet.vk.objects.main.Message;
import ru.blc.objconfig.ConfigurationSection;

import java.util.function.Consumer;
import java.util.function.Function;

public class CallbackButtonAction extends ButtonAction{

    @Getter
    @Setter
    private String label;
    @Getter
    @Setter
    private CallbackFunction onCallbackPress;

    public CallbackButtonAction(String label, CallbackFunction onPress) {
        super("callback", null);
        this.label = label;
        this.onCallbackPress = onPress;
    }

    public CallbackButtonAction(String label, Consumer<VkCommandSender> onPress) {
        super("callback", onPress);
        this.label = label;
    }

    public CallbackButtonAction(String label) {
        super("callback", null);
        this.label = label;
    }

    @Override
    public void write(ConfigurationSection json) {
        super.write(json);
        json.set("label", getLabel());
    }

    @Override
    public void onPress(VkCommandSender sender, Logger logger) {
        if (this.onCallbackPress!=null){
            try{
                String asw = this.onCallbackPress.apply(sender);
                if (asw != null)
                    this.onCallbackPress.answerEvent(asw, sender.getMessage(), (VkBot) sender.getBot());
            }catch (Exception exception){
                logger.error("Error while processing button click", exception);
            }
        }else {
            super.onPress(sender, logger);
        }
    }

    /**
     * Специальный интерфейс имеющий дополнительный метод {@link CallbackFunction#answerEvent(String, Message, VkBot)}
     * для ответа на событие. Метод вызывается после выполнения функции автоматически.<br>
     * Вк апи получит событие show_snackbar с текстом который возвращает функция
     */
    public interface CallbackFunction extends Function<VkCommandSender, String> {

        default void answerEvent(@NotNull String text, @NotNull Message message, @NotNull VkBot bot){
            SendMessageEventAnswer.notAnsweredData.remove(message.getText());
            VkModule.METHODS
                    .messages
                    .sendMessageEventAnswer
                    .getNewParamsSet()
                    .setEventId(message.getText())
                    .setUserId(message.getFromId())
                    .setPeerId(message.getPeerId())
                    .setEventData("{\"type\": \"show_snackbar\", \"text\":\""+text+"\"}")
                    .setToken(bot.getDefaultToken())
                    .call();
        }
    }
}
