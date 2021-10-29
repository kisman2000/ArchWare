package fun.archware.scriptapi.features;

import fun.archware.impl.utils.MoveUtils;
import net.minecraft.entity.Entity;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class ReflectionManager implements Feature {
    @Override
    public String getName() {
        return "controller";
    }

    public static class GetLocalPlayerName extends VarArgFunction implements Feature{

        @Override
        public Varargs invoke(Varargs varargs) {
            return valueOf(mc.player.getName());
        }

        @Override
        public String getName() {
            return "GetLocalPlayerName";
        }
    }

    public static class InvokeVoidMethod extends VarArgFunction implements Feature {

        @Override
        public String getName() {
            return "InvokeVoidMethod";
        }

        @Override
        public Varargs invoke(Varargs varargs) {
            Class<?> clazz = null;
            try{
                clazz = Class.forName(varargs.checkjstring(1));

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Method method = null;
            try {
                method = clazz.getMethod(varargs.checkjstring(2));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            if(!method.isAccessible()) method.setAccessible(true);
            final List<Object> parameters = new ArrayList<>();
            for(int i = 3; i < varargs.checktable(3).length(); ++i){
                parameters.add(varargs.checktable(3).get(i));
            }
            try {
                method.invoke(Modifier.isStatic(method.getModifiers()) ? null : clazz, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return super.invoke(varargs);
        }
    }

    public static class GetBool extends VarArgFunction implements Feature{

        @Override
        public Varargs invoke(Varargs varargs) {
            try {
                final Field field = Entity.class.getDeclaredField(varargs.checkjstring(2));
                field.setAccessible(true);
                return LuaValue.valueOf((boolean)field.get(mc.world.getPlayerEntityByName(varargs.checkjstring(1))));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return LuaValue.valueOf(false);
        }

        @Override
        public String getName() {
            return "GetBool";
        }
    }

    public static class SetBool extends VarArgFunction implements Feature{

        @Override
        public Varargs invoke(Varargs varargs) {
            try {
                final Field field = Entity.class.getDeclaredField(varargs.checkjstring(2));
                field.setAccessible(true);
                field.set(mc.world.getPlayerEntityByName(varargs.checkjstring(1)), varargs.checkboolean(3));
                return NIL;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return NIL;
        }

        @Override
        public String getName() {
            return "setBool";
        }
    }

    public static class GetDouble extends VarArgFunction implements Feature{

        @Override
        public Varargs invoke(Varargs varargs) {
            try {
                final Field field = Entity.class.getDeclaredField(varargs.checkjstring(2));
                field.setAccessible(true);
                return LuaValue.valueOf((double)field.get(mc.world.getPlayerEntityByName(varargs.checkjstring(1))));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return LuaValue.valueOf(false);
        }

        @Override
        public String getName() {
            return "GetDouble";
        }
    }

    public static class SetDouble extends VarArgFunction implements Feature{

        @Override
        public Varargs invoke(Varargs varargs) {
            try {
                final Field field = Entity.class.getDeclaredField(varargs.checkjstring(2));
                field.setAccessible(true);
                field.set(mc.world.getPlayerEntityByName(varargs.checkjstring(1)), varargs.checkdouble(3));
                return NIL;
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
            return NIL;
        }

        @Override
        public String getName() {
            return "setDouble";
        }
    }

    public static class SetSpeed extends OneArgFunction implements Feature{

        @Override
        public String getName() {
            return "setSpeed";
        }

        @Override
        public LuaValue call(LuaValue luaValue) {
            MoveUtils.setSpeed(luaValue.checkdouble());
            return NIL;
        }
    }
}