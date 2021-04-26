package ru.blc.cutlet.api;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.blc.cutlet.api.bot.BotManager;
import ru.blc.cutlet.api.console.Console;
import ru.blc.cutlet.api.module.Module;
import ru.blc.cutlet.api.module.ModuleLoader;

import java.io.*;
import java.util.Properties;

public class Cutlet {

    private static Cutlet instance;

    public static Cutlet instance() {
        return instance;
    }

    private boolean running;

    private final Logger logger = LoggerFactory.getLogger("Cutlet");
    private final Console console;
    private final Properties translations = new Properties();

    @Getter
    private final File botsFolder = new File("bots");
    @Getter
    private final File modulesFolder = new File("modules");
    private final BotManager botManager;
    private final ModuleLoader moduleLoader;


    public Cutlet(){
        instance = this;
        this.moduleLoader = new ModuleLoader(this);
        this.botManager = new BotManager(this);
        this.console = new Console();
    }

    public void start(){
        if (this.isRunning()) throw new IllegalStateException("Cutlet already running");
        this.running = true;
        InputStream in = getResourceAsStream("/messages.properties");
        if (in==null){
            this.getLogger().error("Messages properties not loaded! Translations would not work. Update cutlet or contact with developer.");
        }else {
            try {
                translations.load(in);
            } catch (IOException e) {
                this.getLogger().error("Could not load messages properties", e);
            }
        }
        if (!this.modulesFolder.exists()){
            if (!this.modulesFolder.mkdirs()){
                getLogger().error("Could not create modules folder!");
                shutdown();
            }
        }
        getModuleLoader().detectModules(this.modulesFolder);
        getModuleLoader().loadModules();
        getModuleLoader().enableModules();
        if (!this.botsFolder.exists()){
            if (!this.botsFolder.mkdirs()){
                getLogger().error("Could not create bots folder!");
                shutdown();
            }
        }
        getBotManager().detectBots(botsFolder);
        getBotManager().loadBots();
        getBotManager().enableBots();
    }

    public void shutdown(){
        getLogger().info("Disabling cutlet");
        getBotManager().disableBots();
        getModuleLoader().disableModules();
        getConsole().disable();
        running = false;
    }

    public InputStream getResourceAsStream(@NotNull String name){
        Preconditions.checkArgument(!name.isEmpty(), "name");
        getLogger().debug("Loading cutlet resource {}", name);
        InputStream in = null;
        File resource = new File(name);
        boolean founded = resource.exists() && resource.isFile();
        if (!founded){
            getLogger().debug("No file, try class {}", name);
            try {
                in = this.getClass().getResourceAsStream(name);
            }catch (Exception e){
                this.getLogger().error("Could not load resource"+name, e);
            }
        }else {
            try {
                in = new FileInputStream(resource);
            } catch (FileNotFoundException e) {
                this.getLogger().error("Could not load resource "+name, e);
            }
        }
        getLogger().debug("Resource for {} is {}", name, in);
        return in;
    }

    public String getTranslation(String key){
        return translations.getProperty(key, "No translation for key "+key);
    }

    public boolean isRunning() {
        return running;
    }

    public Logger getLogger() {
        return logger;
    }

    public Console getConsole() {
        return console;
    }

    public BotManager getBotManager() {
        return botManager;
    }

    public ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    /**
     * Возвращает модуль указанного имени
     * @param name имя
     * @return Модуль или null
     */
    public Module getModule(String name){
        return getModuleLoader().getModule(name);
    }

    /**
     * Возвращает модуль указанного типа
     * @param clazz класс моудля
     * @param <T> тип модуля
     * @return Модуль или null
     */
    public <T extends Module> T getModule(Class<T> clazz){
        return getModuleLoader().getModule(clazz);
    }
}
