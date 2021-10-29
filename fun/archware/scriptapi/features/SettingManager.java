package fun.archware.scriptapi.features;

import fun.archware.ArchWare;
import fun.archware.base.setting.Setting;
import fun.archware.base.setting.SettingTextField;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.util.ArrayList;
import java.util.List;

public class SettingManager implements Feature{
    @Override
    public String getName() {
        return "settings";
    }

    public static class AddCheckbox extends VarArgFunction implements Feature{

        @Override
        public Varargs invoke(Varargs varargs) {
            new Setting(varargs.checkjstring(1),
                    varargs.checkjstring(2),
                    ArchWare.moduleManager.getModuleByName(varargs.checkjstring(3)), varargs.checkboolean(4));
            return NIL;
        }

        @Override
        public String getName() {
            return "add_checkbox";
        }
    }

    public static class AddCombobox extends VarArgFunction implements Feature{
        @Override
        public Varargs invoke(Varargs varargs) {

            final List<String> options = new ArrayList<>();
            for(int i = 1; i < varargs.checktable(5).keys().length + 1; ++i){
                options.add(varargs.checktable(5).get(i).checkjstring());
            }
            new Setting(
                    varargs.checkjstring(1),
                    varargs.checkjstring(2),
                    ArchWare.moduleManager.getModuleByName(varargs.checkjstring(3)),
                    varargs.checkjstring(4),
                    options.toArray(new String[options.size()])
            );
            return NIL;
        }

        @Override
        public String getName() {
            return "add_combobox";
        }
    }

    public static class AddNumeric extends VarArgFunction implements Feature {

        @Override
        public Varargs invoke(Varargs varargs) {
            new Setting(
                    varargs.checkjstring(1),
                    varargs.checkjstring(2),
                    ArchWare.moduleManager.getModuleByName(varargs.checkjstring(3)),
                    (float)varargs.checkdouble(4),
                    (float)varargs.checkdouble(5),
                    (float)varargs.checkdouble(6)
            );
            return NIL;
        }

        @Override
        public String getName() {
            return "add_slider";
        }
    }
    public static class AddTextBox extends VarArgFunction implements Feature{
        @Override
        public Varargs invoke(Varargs varargs) {
            final Setting s = new SettingTextField(
              varargs.checkjstring(1),
              varargs.checkjstring(2),
              ArchWare.moduleManager.getModuleByName(varargs.checkjstring(3)),
              varargs.checkjstring(4),
              varargs.checkjstring(5)
                    );
            System.out.println(s.getId());
            return NIL;
        }

        @Override
        public String getName() {
            return "add_textbox";
        }
    }

    public static class GetBooleanValue extends VarArgFunction implements Feature {
        @Override
        public Varargs invoke(Varargs varargs) {
            return LuaValue.valueOf(ArchWare.settingManager.getSettingById(varargs.checkjstring(1)).getValueBoolean());
        }

        @Override
        public String getName() {
            return "GetBooleanValue";
        }
    }
    public static class GetNumericValue extends VarArgFunction implements  Feature{
        @Override
        public Varargs invoke(Varargs varargs) {
            return LuaValue.valueOf(ArchWare.settingManager.getSettingById(varargs.checkjstring(1)).getValueNumeric());
        }

        @Override
        public String getName() {
            return "GetNumericValue";
        }
    }
    public static class GetStringValue extends  VarArgFunction implements Feature{
        @Override
        public Varargs invoke(Varargs varargs) {
            return LuaValue.valueOf(ArchWare.settingManager.getSettingById(varargs.checkjstring(1)).getValueString());

        }

        @Override
        public String getName() {
            return "GetStringValue";
        }
    }

    public static class GetFieldValue extends VarArgFunction implements Feature{
        @Override
        public Varargs invoke(Varargs varargs) {
            return LuaValue.valueOf(ArchWare.settingManager.getSettingById(varargs.checkjstring(1)).getText());
        }

        @Override
        public String getName() {
            return "GetFieldValue";
        }
    }

}
