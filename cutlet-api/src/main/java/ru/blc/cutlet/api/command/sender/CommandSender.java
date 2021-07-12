package ru.blc.cutlet.api.command.sender;

import ru.blc.cutlet.api.bot.Bot;
import ru.blc.cutlet.api.command.Messenger;

/**
 * Отвечает за объекты, которые могут отправлять команды
 */
public interface CommandSender {

    /**
     * @return Бот-владелец. Для консоли null
     */
    Bot getBot();

    /**
     * Проверяет, есть ли у этого отправителя право<br>
     * По умолчанию true для консоли (такое поведение может быть переопределено)
     * @return <code>true</code>, если право есть, в противном случае <code>false</code>
     */
    boolean hasPermission(String permission);

    /**
     * Проверяет, является ли этот отправитель консолью<br>
     * Для стандартной реализации аналогичен коду <code>sender instanceof ConsoleCommandSender</code>
     * @return true, если этот отправитель является консолью
     */
    default boolean isConsole(){
        return false;
    }

    /**
     * Возвращает приватного отправителя.<br>
     * Это работает, если отправитель находится в общем чате (текстовом канале дискорда или беседе вк например).<br>
     * Данный метод вернет отправителя соответсвующего лс с пользователем (если получится)
     * @return приватный оптравитель
     */
    CommandSender getPmSender();

    /**
     * @return true, если необходимо удалять входящие команды и ответы на них если они в лс (по возможности)
     */
    boolean isDeleteIfPM();

    /**
     * @param deleteIfPM true, если необходимо удалять входящие команды и ответы на них если они в лс (по возможности)
     */
    void setDeleteIfPM(boolean deleteIfPM);

    /**
     * @return Имя этого отправителя команды
     */
    String getName();

    /**
     * Отправляет сообщение
     * @param message сообщение
     */
    void sendMessage(String message);

    /**
     * Отправляет сообщение а затем удаляет его со стороны бота, если такое возможно
     * @param message сообщение
     */
    void sendAndDeleteMessage(String message);

    /**
     * Отправляет сложное сообщение<br>
     * Используется в чат-системах (например вк), где сообщение состоит не только из текста
     * @param message сообщение
     */
    void sendMessage(Object message);

    /**
     * Отправляет сложное сообщение а затем удаляет его со стороны бота, если такое возможно
     * @param message сообщение
     */
    void sendAndDeleteMessage(Object message);

    /**
     * @return Мессенджер. По нему определяется, сможет ли запуститься команда. Не должен быть null, за исключением консоли
     */
    Messenger getMessenger();

    /**
     * @return Тип диалога.
     */
    DialogType getDialogType();
}
