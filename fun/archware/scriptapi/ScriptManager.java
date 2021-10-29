package fun.archware.scriptapi;

import fun.archware.impl.managers.FileManager;
import fun.archware.scriptapi.features.Feature;
import fun.archware.scriptapi.features.FeatureManager;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.reflections.Reflections;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScriptManager {
    public static final Map<String[], LuaFunction> REGISTERED_HANDLERS = new HashMap<>();
    public static Globals globals = JsePlatform.standardGlobals();
    public ScriptManager(){
        final Reflections reflections = new Reflections("fun.archware.scriptapi.features");
        final Set<Class<? extends Feature>> classes = reflections.getSubTypesOf(Feature.class);
        classes.forEach(clazz -> {
            final LuaValue luaValue = CoerceJavaToLua.coerce(new FeatureManager());
            try {
                globals.set((String)clazz.getMethod("getName").invoke(clazz.newInstance(), null), luaValue);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                e.printStackTrace();
            }
            final LuaTable t = new LuaTable();
            for(final Class clazz2 : clazz.getClasses()){
                try {
                    t.set((String)clazz2.getMethod("getName").invoke(clazz2.newInstance(), null), (LuaValue) clazz2.newInstance());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
            t.set("__index", t);
            luaValue.setmetatable(t);
        });

    }


    public  void initializeScripts(){
        if(!new File(FileManager.HOME.getAbsolutePath() + "\\scripts").exists()) new File(FileManager.HOME.getAbsolutePath() + "\\scripts").mkdirs();
        for(final File file : new File(FileManager.HOME.getAbsolutePath() + "\\scripts").listFiles()){
            if(file.getName().endsWith(".lua")){
                globals.loadfile(file.getAbsolutePath()).call();
            }
        }
    }
}
