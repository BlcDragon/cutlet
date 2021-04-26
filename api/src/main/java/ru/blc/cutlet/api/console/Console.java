package ru.blc.cutlet.api.console;

import org.slf4j.Logger;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.cutlet.api.bot.BotManager;
import ru.blc.cutlet.api.command.Command;
import ru.blc.cutlet.api.command.sender.ConsoleCommandSender;
import ru.blc.cutlet.api.console.command.StopCommand;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Console implements ConsoleCommandSender {

    private static final AtomicBoolean state = new AtomicBoolean(false);
    private static final Logger LOG = Cutlet.instance().getLogger();

    private final Scanner scanner;
    private boolean stopped = false;
    private BotManager botManager;

    public Console(){
        LOG.info("Enabling console");
        if (state.get()){
            throw new IllegalStateException("Console already started!");
        }else {
            if (!state.compareAndSet(false, true)){
                throw new IllegalStateException("Console already started! Is there multi console starters?");
            }
        }
        Cutlet c = Cutlet.instance();
        scanner = new Scanner(System.in);
        Thread listener = new Thread(()->{
            while (!stopped) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    input(line);
                }
            }
            LOG.info("Console stopped. Cutlet not controlled by console now :c");
        });
        botManager = c.getBotManager();
        listener.setDaemon(false);
        listener.setName("Console");
        listener.start();
        c.getBotManager().registerCommand(null, new StopCommand());


        LOG.info("Console enabled. Wanna cutlet, bro?");
    }

    /**
     * Отключает консоль. После выполнения метода ввод команд невозможен до включения новой консоли.
     */
    public void disable(){
        LOG.info("Disabling console");
        stopped = true;
    }

    protected void input(String input){
        String[] in = input.split(" ");
        Command command = botManager.getCommand(in[0]);
        if (command!=null) {
            try {
                command.dispatch(this, Arrays.copyOfRange(in, 1, in.length));
            }catch (Exception e){
                LOG.error("Error while dispatching command", e);
            }
        }else {
            sendMessage("Command "+in[0]+" not founded");
        }
    }

    @Override
    public void sendMessage(String message) {
        LOG.info(message);
    }

    @Override
    public void sendMessage(Object message) {
        LOG.info("Hard message for you: {}", message);
    }
}
