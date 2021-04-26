package ru.blc.cutlet.api.module;

import com.google.common.base.Preconditions;
import ru.blc.cutlet.api.Cutlet;
import ru.blc.objconfig.yml.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ModuleLoader {

    private Cutlet cutlet;

    private final Map<String, Module> modules = new HashMap<>();
    private Map<String, ModuleDescription> toLoad = new HashMap<>();
    private final Map<Module, Boolean> enabled = new HashMap<>();

    public ModuleLoader(Cutlet cutlet){
        this.cutlet = cutlet;
    }

    public Cutlet getCutlet() {
        return cutlet;
    }

    /**
     * Возвращает модуль указанного имени
     * @param name имя
     * @return Модуль или null
     */
    public Module getModule(String name){
        return this.modules.get(name);
    }

    /**
     * Возвращает модуль указанного типа
     * @param clazz класс моудля
     * @param <T> тип модуля
     * @return Модуль или null
     */
    @SuppressWarnings("unchecked")
    public <T extends Module> T getModule(Class<T> clazz){
        T r = null;
        for (Module value : this.modules.values()) {
            if (value.getClass()==clazz){
                r = (T) value;
                break;
            }
        }
        return r;
    }

    public void detectModules(File folder){
        Preconditions.checkNotNull(folder, "modules folder");
        Preconditions.checkArgument(folder.isDirectory(), "should be directory");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".jar")){
                try {
                    JarFile jar = new JarFile(file);
                    Throwable t1 = null;
                    try {
                        JarEntry desc = jar.getJarEntry("module.yml");
                        Preconditions.checkNotNull(desc, "module should has module.yml");
                        Throwable t2 = null;
                        InputStream in = jar.getInputStream(desc);
                        try {
                            ModuleDescription description = YamlConfiguration.loadToType(new InputStreamReader(in), ModuleDescription.class);
                            Preconditions.checkNotNull(description.getName(), "Module from %s has not name", file);
                            Preconditions.checkNotNull(description.getMain(), "Module from %s has not main", file);
                            description.setFile(file);
                            if (this.toLoad.containsKey(description.getName())){
                                throw new IllegalStateException("Duplicate modules "+description.getName()+ " at "+ file+" and "
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
                    cutlet.getLogger().error("Could not load module from file "+file, e);
                }
            }
        }

    }

    public void loadModules(){
        Map<ModuleDescription, Boolean> stats = new HashMap<>();

        toLoad.values().forEach(bd-> loadModule(bd, new Stack<>(), stats));

        toLoad.clear();
        toLoad = null;
    }

    protected boolean loadModule(ModuleDescription description, Stack<ModuleDescription> dependencies, Map<ModuleDescription, Boolean> stats){
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
                ModuleDescription dependency = toLoad.get(s);
                if (dependency==null){
                    state = false;
                    getCutlet().getLogger().warn("Could not load module {}. Dependency {} not founded", name, s);
                    break;
                }
                if (dependencies.contains(dependency)){
                    //Recursive dependencies
                    String way = dependency.getName() +" -> "+
                            dependencies.stream().map(ModuleDescription::getName).collect(Collectors.joining(" -> "));
                    state = false;
                    getCutlet().getLogger().warn("Could not load module {}. Recursive dependencies detected {}", name, way);
                    break;
                }
                if (!loadModule(dependency, dependencies, stats)){
                    state = false;
                    getCutlet().getLogger().warn("Could not load module {}. Failed loading dependency {}", name, s);
                    break;
                }
            }
        }
        if (state){
            try{
                URLClassLoader loader = new ModuleClassLoader(cutlet, description.getFile(), description);
                Class<?> mainClazz = loader.loadClass(description.getMain());
                Module clazz = (Module)mainClazz.getDeclaredConstructor().newInstance();
                this.modules.put(clazz.getName(), clazz);
                clazz.onLoad();
                getCutlet().getLogger().info("Loaded module {} version {} by {}", description.getName(), description.getVersion(), description.getAuthors());
            }catch (Exception e){
                state = false;
                getCutlet().getLogger().error("Could not load module "+ description.getName(), e);
            }
        }
        stats.put(description, state);
        return state;
    }

    public void enableModules(){
        this.modules.values().forEach(b-> enableModule(b, enabled));
    }

    protected boolean enableModule(Module module, Map<Module, Boolean> stats){
        Set<String> depend = new HashSet<>(module.getDescription().getSoftDepends());
        boolean status = true;
        for (String s : depend) {
            Module softDependency = getModule(s);
            if (!enableModule(softDependency, stats)){
                status = false;
                getCutlet().getLogger().warn("Failed to enable module {} via dependency {} can not be enabled", module.getName(), s);
                break;
            }
        }
        if (status){
            try{
                enabled.put(module, true);
                module.onEnable();
                getCutlet().getLogger().info("Enabled module {} version {} by {}", module.getName(), module.getDescription().getVersion(), module.getDescription().getAuthors());
            }catch (Exception e){
                status = false;
                getCutlet().getLogger().error("Exception while enabling module "+module.getName(), e);
            }
        }
        stats.put(module, status);
        return status;
    }

    public void disableModules(){
        for (Module module : this.modules.values()) {
            if (enabled.getOrDefault(module, false)){
                try {
                    module.onDisable();
                }catch (Exception e){
                    getCutlet().getLogger().error("Exception while disabling module "+module.getName(), e);
                }finally {
                    getCutlet().getLogger().info("Disabled module {} version {} by {}", module.getName(), module.getDescription().getVersion(), module.getDescription().getAuthors());
                }
            }
        }
    }
}
