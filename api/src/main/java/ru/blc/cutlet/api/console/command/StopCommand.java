package ru.blc.cutlet.api.console.command;

import ru.blc.cutlet.api.Cutlet;

public class StopCommand extends ConsoleCommand{
    public StopCommand() {
        super("stop", "command.stop", "stops cutlet", "");
        setCommandExecutor((command, sender, alias, args)-> Cutlet.instance().shutdown());
    }
}
