package ru.blc.cutlet.api.bot;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.objconfig.yml.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Bot {

    @Getter
    private BotDescription description;
    @Getter
    private Cutlet cutlet;
    @Getter
    private Logger logger;
    @Getter
    private File directory;
    @Getter
    private YamlConfiguration config;

    public Bot(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        Preconditions.checkState(classLoader instanceof BotClassLoader, "Bot requires " + BotClassLoader.class.getName());
        ((BotClassLoader)classLoader).init(this);
    }

    final public String getName() {
        return getDescription().getName();
    }

    final public void init(Cutlet cutlet, BotDescription description){
        this.cutlet = cutlet;
        this.description = description;
        this.logger = LoggerFactory.getLogger(getName());
        this.directory = new File(cutlet.getBotsFolder(), getName());
        config = new YamlConfiguration();
        try {
            InputStream in = getResourceAsStream("/config.yml");
            if (in!=null){
                config.load(new InputStreamReader(in, StandardCharsets.UTF_8));
            }
        }catch (Exception e){
            getLogger().error("Failed to load configuration", e);
        }
    }

    public void saveDefaultConfig(){
        File configFile = new File(getDirectory(), "config.yml");
        if (!configFile.exists()){
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                getLogger().error("Failed to create config file", e);
                return;
            }
        }else {
            return;
        }
        InputStream in = this.getClass().getResourceAsStream("/config.yml");
        if (in==null){
            getLogger().error("Failed to save default config. No config file at plugin");
            return;
        }
        try {
            FileOutputStream out = new FileOutputStream(configFile);
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            getLogger().error("Failed to save default config.", e);
        }
        try {
            config.load(configFile);
        }catch (Exception e){
            getLogger().error("Failed to load configuration", e);
        }
    }

    public InputStream getResourceAsStream(@NotNull String name){
        Preconditions.checkArgument(!name.isEmpty(), "name");
        InputStream in = null;
        File resource = new File(name);
        boolean founded = resource.exists() && resource.isFile();
        if (!founded){
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
        return in;
    }

    public void onLoad(){}
    public void onEnable(){}
    public void onDisable(){}
}
