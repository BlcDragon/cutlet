package ru.blc.cutlet.api.command.sender;

public interface ConsoleCommandSender extends CommandSender{

    @Override
    default boolean hasPermission(String permission){
        return true;
    }

    @Override
    default boolean isConsole() {
        return true;
    }

    @Override
    default String getName(){
        return "Console";
    }
}
