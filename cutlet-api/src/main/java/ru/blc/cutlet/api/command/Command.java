package ru.blc.cutlet.api.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.cutlet.api.bot.Bot;
import ru.blc.cutlet.api.command.sender.CommandSender;
import ru.blc.cutlet.api.command.sender.DialogType;

import java.util.Arrays;
import java.util.List;

public class Command {

    public static final CommandExecutor DEFAULT_EXECUTOR = (command, sender, alias, args) -> sender.sendMessage("Not implemented yet");

    @NotNull
    private final String name;
    @NotNull
    private final String permission;
    @NotNull
    private final String description;
    @NotNull
    private final String usage;
    @NotNull
    private final DialogType dialogType;
    private final String[] aliases;
    private final Bot owner;
    private Messenger[] allowedMessengers;

    private CommandExecutor executor;

    public Command(Bot owner, @NotNull DialogType dialogType, @NotNull String name, @Nullable String permission,
                   @Nullable String description, @Nullable String usage, String...aliases){
        this.owner = owner;
        this.name = name;
        if (permission==null) permission = "";
        this.permission = permission;
        if (description==null) description = "";
        this.description = description;
        if (usage==null) usage = "";
        this.usage = usage;
        this.aliases = aliases;
        this.executor = DEFAULT_EXECUTOR;
        this.dialogType = dialogType;
    }

    public Bot getOwner() {
        return owner;
    }

    /**
     * Устанавливает разрешенные мессенджеры. Если параметр не установлен - разрешены любые
     * @param messengers мессенджеры
     */
    public void setAllowedMessengers(Messenger... messengers){
        this.allowedMessengers = messengers;
    }

    /**
     * Проверяет разрешен ли этот мессенджер
     * @param messenger мессенджер
     * @return true, если мессенджер разрешен
     */
    public boolean isAllowed(Messenger messenger){
        if (messenger==null) return true;
        if (allowedMessengers==null) return true;
        if (allowedMessengers.length==0) return true;
        return Arrays.stream(allowedMessengers).anyMatch(m->m==messenger);
    }

    /**
     * Выполняет команду.<br>
     * Если у отправителя нет прав, команда выполнена не будет, а отправитель получит соответствующее сообщение
     * @param sender отправитель
     * @throws NullPointerException если sender == null
     */
    public void dispatch(@NotNull CommandSender sender, String alias, String...args){
        if (!sender.hasPermission(getPermission())){
            sender.sendMessage(Cutlet.instance().getTranslation("no_permission"));
            return;
        }
        if (isOnlyConsole()&&!sender.isConsole()){
            sender.sendMessage(Cutlet.instance().getTranslation("only_console"));
            return;
        }
        if (!isAllowed(sender.getMessenger())){
            sender.sendMessage(Cutlet.instance().getTranslation("unsupported_messenger"));
        }
        try{
            getCommandExecutor().onCommand(this, sender, alias, args);
        }catch (Exception e){
            sender.sendMessage(Cutlet.instance().getTranslation("command_error"));
            owner.getLogger().error("Error while dispatching command "+getName(), e);
        }
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
    public String getHelpMessage(){
        return getDescription();
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

    @NotNull
    public DialogType getDialogType() {
        return dialogType;
    }

    public CommandExecutor getCommandExecutor(){
        return executor;
    }

    public void setCommandExecutor(CommandExecutor executor) {
        if (executor==null) executor = DEFAULT_EXECUTOR;
        this.executor = executor;
    }
}
