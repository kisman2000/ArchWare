package fun.archware.scriptapi.features;

import fun.archware.ArchWare;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.scriptapi.ScriptManager;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class FeatureManager implements Feature {
    @Override
    public String getName() {
        return "client";
    }

    public static class RegisterFeature extends TwoArgFunction implements Feature{

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
            ScriptManager.globals.set("SCRIPT_NAME", luaValue.toString());
            //ArchWare.moduleManager.modules.add(new Module(luaValue.toString(), Category.LUA));

            //todo: fix
            return NIL;
        }

        @Override
        public String getName() {
            return "register_module";
        }
    }

    public static class RegisterCallBack extends TwoArgFunction implements Feature{

        @Override
        public LuaValue call(LuaValue luaValue, LuaValue luaValue1) {
            ScriptManager.REGISTERED_HANDLERS.put(new String[]{
                    ScriptManager.globals.get("SCRIPT_NAME").toString(),
                    luaValue.toString()
            }, luaValue1.checkfunction());
            return NIL;
        }

        @Override
        public String getName() {
            return "register_callback";
        }
    }
}
