package ru.blc.cutlet.api.console.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.blc.cutlet.api.command.Command;

public abstract class ConsoleCommand extends Command {

    public ConsoleCommand(@NotNull String name, @Nullable String permission, @Nullable String description, @Nullable String usage, String... aliases) {
        super(name, permission, description, usage, aliases);
    }

    @Override
    public boolean isOnlyConsole() {
        return true;
    }
}
