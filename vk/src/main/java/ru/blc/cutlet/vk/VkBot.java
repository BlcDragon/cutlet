package ru.blc.cutlet.vk;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import ru.blc.cutlet.vk.command.SimpleCommandSender;
import ru.blc.cutlet.vk.command.VkCommandSender;
import ru.blc.cutlet.vk.objects.main.Message;

public interface VkBot {

    /**
     * @return Айди группы этого бота
     */
    int getGroupId();

    /**
     * @return Токен по умолчанию для этого бота
     */
    AccessToken getDefaultToken();

    /**
     * Строка-подтверждение для этого бота. Может быть null, если не используется CallbackApi
     * @return поддтверждение
     */
    String getConfirmation();

    void setConfirmation(String confirmation);

    /**
     * @return Имя бота
     */
    String getName();

    /**
     * @return секретный ключ. Для LongPoll допускается пустая строка
     */
    String getSecret();

    /**
     * @return Логгер бота
     */
    Logger getLogger();

    /**
     * Последовательность символов, с которых начинается команда. По умолчанию "/"
     * @return префикс команд
     */
    default String getCommandsPrefix(){
        return "/";
    }

    default VkCommandSender getCommandSender(Message message){
        return new SimpleCommandSender(message, this);
    }


    /**
     * @return Обработчик событий. Если null, используется стандартный
     */
    @Nullable
    default JsonHandler getJsonHandler(){
        return null;
    }
}
