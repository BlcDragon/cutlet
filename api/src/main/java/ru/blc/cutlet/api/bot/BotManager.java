package ru.blc.cutlet.api.bot;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.cutlet.api.command.Command;
import ru.blc.cutlet.api.event.*;
import ru.blc.cutlet.api.event.bot.BotDisabledEvent;
import ru.blc.cutlet.api.event.bot.BotEnabledEvent;
import ru.blc.objconfig.yml.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class BotManager {

    private final Cutlet cutlet;
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<Bot, Collection<Command>> commandsByBots = new HashMap<>();

    private final Map<String, Bot> bots = new HashMap<>();
    private Map<String, BotDescription> toLoad = new HashMap<>();
    private final Map<Bot, Boolean> enabled = new HashMap<>();

    public BotManager(Cutlet cutlet){
        this.cutlet = cutlet;
    }

    public Cutlet getCutlet() {
        return cutlet;
    }

    /**
     * Регистрирует команду<br>
     * Если имя или хотя бы один алиас команды уже занят команда не будет зарегистрирована
     * @param bot бот владелец
     * @param command команда
     * @return true, если регистрация успешна, в противном случае false
     */
    public boolean registerCommand(Bot bot, @NotNull Command command){
        if (commands.containsKey(command.getName().toLowerCase(Locale.ROOT))) return false;
        for (String alias : command.getAliases()) {
            if (commands.containsKey(alias.toLowerCase(Locale.ROOT))) return false;
        }
        commands.put(command.getName().toLowerCase(Locale.ROOT), command);
        for (String alias : command.getAliases()) {
            commands.put(alias.toLowerCase(Locale.ROOT), command);
        }
        Collection<Command> cmds = commandsByBots.computeIfAbsent(bot, k -> new HashSet<>());
        cmds.add(command);
        return true;
    }

    /**
     * Удаляет команду
     * @param command команда
     */
    public void unregisterCommand(Command command){
        commands.values().remove(command);
        commandsByBots.values().forEach(c->c.remove(command));
    }

    /**
     * Удаляет все команды которыми владеет этот бот
     * @param bot бот
     */
    public void unregisterCommands(Bot bot){
        Collection<Command> cmds = commandsByBots.get(bot);
        if (cmds!=null){
            commands.values().removeAll(cmds);
        }
    }

    /**
     * Получает команду по имени или алиасу
     * @param alias имя команды или алиас
     * @return команду или null
     */
    public Command getCommand(String alias){
        return commands.get(alias.toLowerCase(Locale.ROOT));
    }

    /**
     * Возвращает бота указанного имени
     * @param name имя
     * @return Бота или null
     */
    public Bot getBot(String name){
        return this.bots.get(name);
    }

    /**
     * Возвращает бота указанного типа
     * @param clazz класс бота
     * @param <T> тип бота
     * @return Бота или null
     */
    @SuppressWarnings("unchecked")
    public <T extends Bot> T getBot(Class<T> clazz){
        T r = null;
        for (Bot value : this.bots.values()) {
            if (value.getClass()==clazz){
                r = (T) value;
                break;
            }
        }
        return r;
    }

    public void detectBots(File folder){
        Preconditions.checkNotNull(folder, "bots folder");
        Preconditions.checkArgument(folder.isDirectory(), "should be directory");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".jar")){
                try {
                    JarFile jar = new JarFile(file);
                    Throwable t1 = null;
                    try {
                        JarEntry desc = jar.getJarEntry("bot.yml");
                        Preconditions.checkNotNull(desc, "Bot should has bot.yml");
                        Throwable t2 = null;
                        InputStream in = jar.getInputStream(desc);
                        try {
                            BotDescription description = YamlConfiguration.loadToType(new InputStreamReader(in), BotDescription.class);
                            Preconditions.checkNotNull(description.getName(), "Bot from %s has not name", file);
                            Preconditions.checkNotNull(description.getMain(), "Bot from %s has not main", file);
                            description.setFile(file);
                            if (this.toLoad.containsKey(description.getName())){
                                throw new IllegalStateException("Duplicate bots "+description.getName()+ " at "+ file+" and "
                                        +toLoad.get(description.getName()).getFile());
                            }
                            this.toLoad.put(description.getName(), description);
                        } catch (Throwable t) {
                            t2 = t;
                            throw t;
                        } finally {
                            if (in != null) {
                                if (t2 != null) {
                                    try {
                                        in.close();
                                    } catch (Throwable t) {
                                        t2.addSuppressed(t);
                                    }
                                } else {
                                    in.close();
                                }
                            }
                        }
                    }catch (Throwable t){
                        t1 = t;
                        throw t;
                    }finally {
                        if (t1 != null) {
                            try {
                                jar.close();
                            } catch (Throwable t) {
                                t1.addSuppressed(t);
                            }
                        } else {
                            jar.close();
                        }
                    }
                } catch (Exception e) {
                    cutlet.getLogger().error("Could not load bot from file "+file, e);
                }
            }
        }

    }

    public void loadBots(){
        Map<BotDescription, Boolean> stats = new HashMap<>();

        toLoad.values().forEach(bd->loadBot(bd, new Stack<>(), stats));

        toLoad.clear();
        toLoad = null;
    }

    protected boolean loadBot(BotDescription description, Stack<BotDescription> dependencies, Map<BotDescription, Boolean> stats){
        boolean state;
        if (stats.containsKey(description)){
            return stats.get(description);
        }else {
            String name = description.getName();
            dependencies.add(description);
            Set<String> depend = new HashSet<>();
            depend.addAll(description.getDepends());
            depend.addAll(description.getSoftDepends());
            state = true;
            for (String s : depend) {
                BotDescription dependency = toLoad.get(s);
                if (dependency==null){
                    state = false;
                    getCutlet().getLogger().warn("Could not load bot {}. Dependency {} not founded", name, s);
                    break;
                }
                if (dependencies.contains(dependency)){
                    //Recursive dependencies
                    String way = dependency.getName() +" -> "+
                            dependencies.stream().map(BotDescription::getName).collect(Collectors.joining(" -> "));
                    state = false;
                    getCutlet().getLogger().warn("Could not load bot {}. Recursive dependencies detected {}", name, way);
                    break;
                }
                if (!loadBot(dependency, dependencies, stats)){
                    state = false;
                    getCutlet().getLogger().warn("Could not load bot {}. Failed loading dependency {}", name, s);
                    break;
                }
            }
        }
        if (state){
            try{
                URLClassLoader loader = new BotClassLoader(cutlet, description.getFile(), description);
                Class<?> mainClazz = loader.loadClass(description.getMain());
                Bot clazz = (Bot)mainClazz.getDeclaredConstructor().newInstance();
                this.bots.put(clazz.getName(), clazz);
                clazz.onLoad();
                getCutlet().getLogger().info("Loaded bot {} version {} by {}", description.getName(), description.getVersion(), description.getAuthors());
            }catch (Exception e){
                state = false;
                getCutlet().getLogger().error("Could not load bot "+ description.getName(), e);
            }
        }
        stats.put(description, state);
        return state;
    }

    public void enableBots(){
        this.bots.values().forEach(b->enableBot(b, enabled));
    }

    protected boolean enableBot(Bot bot, Map<Bot, Boolean> stats){
        Set<String> depend = new HashSet<>(bot.getDescription().getSoftDepends());
        boolean status = true;
        for (String s : depend) {
            Bot softDependency = getBot(s);
            if (!enableBot(softDependency, stats)){
                status = false;
                getCutlet().getLogger().warn("Failed to enable bot {} via dependency {} can not be enabled", bot.getName(), s);
                break;
            }
        }
        if (status){
            try{
                enabled.put(bot, true);
                bot.onEnable();
                callEvent(new BotEnabledEvent(bot));
                getCutlet().getLogger().info("Enabled bot {} version {} by {}", bot.getName(), bot.getDescription().getVersion(), bot.getDescription().getAuthors());
            }catch (Exception e){
                status = false;
                getCutlet().getLogger().error("Exception while enabling bot "+bot.getName(), e);
                try {
                    HandlerList.unregisterAll(bot);
                    this.unregisterCommands(bot);
                }catch (Exception ignored){}
            }
        }
        stats.put(bot, status);
        return status;
    }

    public void disableBots(){
        for (Bot bot : this.bots.values()) {
            if (enabled.getOrDefault(bot, false)){
                try {
                    bot.onDisable();
                }catch (Exception e){
                    getCutlet().getLogger().error("Exception while disabling bot "+bot.getName(), e);
                }finally {
                    HandlerList.unregisterAll(bot);
                    this.unregisterCommands(bot);
                    callEvent(new BotDisabledEvent(bot));
                    getCutlet().getLogger().info("Disabled bot {} version {} by {}", bot.getName(), bot.getDescription().getVersion(), bot.getDescription().getAuthors());
                }
            }
        }
    }

    /**
     * Запускает событие без фильтации получателей
     * @param event событие
     */
    public void callEvent(Event event) {
        this.callEvent(event, null);
    }

    /**
     * Запускает событие с фильтрацией получателей
     * @param event событие
     * @param filter фильтр
     */
    public void callEvent(Event event, Predicate<Bot> filter) {
        this.fireEvent(event, filter);
    }

    private void fireEvent(Event event, Predicate<Bot> filter) {
        HandlerList handlers = event.getHandlers();
        RegisteredListener[] listeners = handlers.getRegisteredListeners();
        for (RegisteredListener registration : listeners) {
            try {
                registration.callEvent(event, filter);
            } catch (Throwable arg10) {
                getCutlet().getLogger().warn("Could not pass event " + event.getEventName() + " to "
                        + registration.getBot().getName(), arg10);
            }
        }

    }

    @SuppressWarnings("unchecked")
    public void registerEvents(Bot bot, Listener listener) {
        Preconditions.checkNotNull(bot, "bot");
        Preconditions.checkNotNull(listener, "Listener");
        if (!enabled.getOrDefault(bot, false)){
            getCutlet().getLogger().warn("{} attempted to register events while not enabled!", bot.getName());
            return;
        }
        HashMap<Class<? extends Event>, Set<RegisteredListener>> map = new HashMap<>();

        for (Method m : listener.getClass().getMethods()) {
            if (m.isAnnotationPresent(EventHandler.class)) {
                if (m.getParameterTypes().length == 1 && Event.class.isAssignableFrom(m.getParameterTypes()[0])) {
                    map.put((Class<? extends Event>) m.getParameterTypes()[0], new HashSet<>());
                } else {
                    bot.getLogger().warn("{} attempted to register an invalid EventHandler method signature \"{}\" in {}",
                            bot.getName(), m.toGenericString(), listener.getClass());
                }
            }
        }
        for (Method m : listener.getClass().getMethods()) {
            if (m.isAnnotationPresent(EventHandler.class)) {
                if (m.getParameterTypes().length == 1 && Event.class.isAssignableFrom(m.getParameterTypes()[0])) {

                    map.get(m.getParameterTypes()[0]).add(new RegisteredListener(listener, new BotManagerEventExecutor(m.getParameterTypes()[0], m),
                            m.getAnnotation(EventHandler.class).eventPriority(), bot,
                            m.getAnnotation(EventHandler.class).ignoreCancelled(),
                            m.getAnnotation(EventHandler.class).ignoreFilter()));
                }
            }
        }

        for (Map.Entry<Class<? extends Event>,Set<RegisteredListener>> entry:map.entrySet()) {
            this.getEventListeners(this.getRegistrationClass(entry.getKey()))
                    .registerAll(entry.getValue());
        }
    }

    private HandlerList getEventListeners(Class<? extends Event> type) {
        try {
            Method e = this.getRegistrationClass(type).getDeclaredMethod("getHandlerList");
            e.setAccessible(true);
            return (HandlerList) e.invoke(null, new Object[0]);
        } catch (Exception arg2) {
            throw new RuntimeException(arg2.toString());
        }
    }


    private Class<? extends Event> getRegistrationClass(Class<? extends Event> clazz) {
        try {
            clazz.getDeclaredMethod("getHandlerList");
            return clazz;
        } catch (NoSuchMethodException arg1) {
            if (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Event.class) && Event.class.isAssignableFrom(clazz.getSuperclass())) {
                return this.getRegistrationClass(clazz.getSuperclass().asSubclass(Event.class));
            } else {
                throw new RuntimeException("Unable to find handler list for event " + clazz.getName() + ". Static getHandlerList method required!");
            }
        }
    }

    static class BotManagerEventExecutor implements EventExecutor {

        private final Class<?> clazz;
        private final Method method;

        public BotManagerEventExecutor(Class<?> clazz, Method method) {
            this.clazz=clazz;
            this.method = method;
        }

        @Override
        public void execute(Listener listener, Event event) throws EventException {
            if (clazz.isAssignableFrom(event.getClass())) {
                try {
                    method.invoke(listener, event);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new EventException("Failed execute event ",e);
                }
            }
        }
    }
}
