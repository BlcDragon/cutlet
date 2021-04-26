package ru.blc.cutlet.api.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.cutlet.api.command.sender.CommandSender;

import java.util.Arrays;
import java.util.List;

public class Command {

    public static final CommandExecutor DEFAULT_EXECUTOR = (command, sender, alias, args) -> sender.sendMessage("Not implemented yet");

    @NotNull
    private final String name,permission,description,usage;
    private final String[] aliases;

    private CommandExecutor executor;

    public Command(@NotNull String name, @Nullable String permission, @Nullable String description, @Nullable String usage, String...aliases){
        this.name = name;
        if (permission==null) permission = "";
        this.permission = permission;
        if (description==null) description = "";
        this.description = description;
        if (usage==null) usage = "";
        this.usage = usage;
        this.aliases = aliases;
        this.executor = DEFAULT_EXECUTOR;
    }

    /**
     * Выполняет команду.<br>
     * Если у отправителя нет прав, команда выполнена не будет, а отправитель получит соответствующее сообщение
     * @param sender отправитель
     * @throws NullPointerException если sender == null
     */
    public void dispatch(@NotNull CommandSender sender, String...args){
        if (!sender.hasPermission(getPermission())){
            sender.sendMessage(Cutlet.instance().getTranslation("no_permission"));
            return;
        }
        getCommandExecutor().onCommand(this, sender, getName(), args);
    }

    /**
     * @return Имя команды
     */
    @NotNull
    public String getName(){
        return this.name;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    @NotNull
    public String getUsage() {
        return usage;
    }

    /**
     * @return Список алиасов без обратной связи. Имя не считаетя алиасом
     */
    public List<String> getAliases(){
        return Arrays.asList(aliases);
    }

    /**
     * @return Право для выполнения команды. Если права нет, то пустая строка
     */
    @NotNull public String getPermission(){
        return this.permission;
    }

    /**
     * Проверяет, является ли эта команда только консольной
     * @return true, если команда предназначена только для консоли
     */
    public boolean isOnlyConsole(){
        return false;
    }

    public CommandExecutor getCommandExecutor(){
        return executor;
    }

    public void setCommandExecutor(CommandExecutor executor) {
        if (executor==null) executor = DEFAULT_EXECUTOR;
        this.executor = executor;
    }
}
