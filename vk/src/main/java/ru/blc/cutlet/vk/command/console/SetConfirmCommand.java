package ru.blc.cutlet.vk.command.console;

import ru.blc.cutlet.api.Cutlet;
import ru.blc.cutlet.api.bot.Bot;
import ru.blc.cutlet.api.command.Command;
import ru.blc.cutlet.api.command.CommandExecutor;
import ru.blc.cutlet.api.command.sender.CommandSender;
import ru.blc.cutlet.api.console.command.ConsoleCommand;
import ru.blc.cutlet.vk.VkBot;
import ru.blc.cutlet.vk.VkModule;

public class SetConfirmCommand extends ConsoleCommand {
    public SetConfirmCommand() {
        super("setconfirm", "command.setconfirm", "sets confirm", "set confirm <bot> <confirm>", "scf");
        setCommandExecutor(new CommandExecutor() {
            @Override
            public void onCommand(Command command, CommandSender sender, String alias, String[] args) {
                if (args.length<2){
                    sender.sendMessage(getUsage());
                    return;
                }
                Bot bot = Cutlet.instance().getBotManager().getBot(args[0]);
                if (bot==null){
                    sender.sendMessage("No bot "+args[0]);
                    return;
                }
                if (bot instanceof VkBot){
                    ((VkBot) bot).setConfirmation(args[1]);
                    sender.sendMessage("New confirmation for bot "+args[0]+" is "+args[1]);
                }else {
                    sender.sendMessage(bot.getName() +" is not vk bot");
                }
            }
        });
        setAllowedMessengers(VkModule.VK_MESSENGER);
    }

    @Override
    public boolean isOnlyConsole() {
        return false;
    }
}
