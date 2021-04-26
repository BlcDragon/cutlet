package ru.blc.cutlet.api.command;

import ru.blc.cutlet.api.command.sender.CommandSender;

/**
 * Отвечает за работу команды
 */
public interface CommandExecutor {

    /**
     * Выполняет работу команды
     * @param command команда
     * @param sender отпарвитель
     * @param alias алиас команды, который был использован в данном случае
     * @param args аргументы команды
     */
    void onCommand(Command command, CommandSender sender, String alias, String[] args);
}
