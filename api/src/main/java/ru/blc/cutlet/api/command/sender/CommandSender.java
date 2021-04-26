package ru.blc.cutlet.api.command.sender;

/**
 * Отвечает за объекты, которые могут отправлять команды
 */
public interface CommandSender {

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
     * @return Имя этого отправителя команды
     */
    String getName();

    /**
     * Отправляет сообщение
     * @param message сообщение
     */
    void sendMessage(String message);

    /**
     * Отправляет сложное сообщение<br>
     * Используется в чат-системах (например вк), где сообщение состоит не только из текста
     * @param message сообщение
     */
    void sendMessage(Object message);
}
