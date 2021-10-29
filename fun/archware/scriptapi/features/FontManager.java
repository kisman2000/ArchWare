package fun.archware.scriptapi.features;

import fun.archware.ArchWare;
import fun.archware.base.module.Module;
import fun.archware.impl.utils.font.CustomFontRenderer;
import fun.archware.scriptapi.ScriptManager;
import net.minecraft.util.text.TextFormatting;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FontManager implements Feature {
    private static final List<CustomFontRenderer> REGISTERED_FONTS = new ArrayList<>();

    public FontManager(){
        System.out.printf("woo");
        ScriptManager.globals.set("FONT_PLAIN", Font.PLAIN);
        ScriptManager.globals.set("FONT_BOLD", Font.BOLD);
        ScriptManager.globals.set("FONT_ITALIC", Font.ITALIC);
    }

    public static class CreateNewInstance extends VarArgFunction implements Feature {

        @Override
        public Varargs invoke(Varargs varargs) {
            if(REGISTERED_FONTS.stream().anyMatch(
                    font -> font.getFont().getName().equals(varargs.checkjstring(1))
                    && font.getFont().getSize() == varargs.checkint(3)
                    && font.getFont().getStyle() == varargs.checkint(2))

            ) {
                ArchWare.sendChatMessage(TextFormatting.RED + "SCRIPTAPI_EXCEPTION: This font is already registered.");
                return NIL;
            }
            REGISTERED_FONTS.add(new CustomFontRenderer(new Font(varargs.checkjstring(1), varargs.checkint(2), varargs.checkint(3)), true, true));
            System.out.println("done");

            return LuaValue.valueOf(true);
        }

        @Override
        public String getName() {
            return "create_new_instance";
        }
    }

    public static class DrawString extends VarArgFunction implements Feature {
        @Override
        public Varargs invoke(Varargs varargs) {
            if(!REGISTERED_FONTS.stream().anyMatch(
                    font -> font.getFont().getName().equals(varargs.checkjstring(1))
                            && font.getFont().getSize() == varargs.checkint(3)
                            && font.getFont().getStyle() == varargs.checkint(2))
            ){
                ArchWare.sendChatMessage(TextFormatting.RED + "SCRIPTAPI_EXCEPTION: This font is not registered. Please call create_new_instance");
                return NIL;
            }
            REGISTERED_FONTS.stream().filter(
                    font -> font.getFont().getName().equals(varargs.checkjstring(1))
                            && font.getFont().getSize() == varargs.checkint(3)
                            && font.getFont().getStyle() == varargs.checkint(2)
            ).findFirst().get().drawString(varargs.checkjstring(4), varargs.checkdouble(5), varargs.checkdouble(6), varargs.checkint(7));
            return NIL;
        }

        @Override
        public String getName() {
            return "drawString";
        }
    }

    public static class DrawStringWithShadow extends VarArgFunction implements Feature {
        @Override
        public Varargs invoke(Varargs varargs) {
            if(!REGISTERED_FONTS.stream().anyMatch(
                    font -> font.getFont().getName().equals(varargs.checkjstring(1))
                            && font.getFont().getSize() == varargs.checkint(3)
                            && font.getFont().getStyle() == varargs.checkint(2))
            ){
                ArchWare.sendChatMessage(TextFormatting.RED + "SCRIPTAPI_EXCEPTION: This font is not registered. Please call create_new_instance");
                return NIL;
            }
            REGISTERED_FONTS.stream().filter(
                    font -> font.getFont().getName().equals(varargs.checkjstring(1))
                            && font.getFont().getSize() == varargs.checkint(3)
                            && font.getFont().getStyle() == varargs.checkint(2)
            ).findFirst().get().drawStringWithShadow(varargs.checkjstring(4), varargs.checkdouble(5), varargs.checkdouble(6), varargs.checkint(7));
            return NIL;
        }

        @Override
        public String getName() {
            return "drawStringWithShadow";
        }
    }

    public static class GetStringWidth extends VarArgFunction implements Feature {
        @Override
        public Varargs invoke(Varargs varargs) {
            if(!REGISTERED_FONTS.stream().anyMatch(
                    font -> font.getFont().getName().equals(varargs.checkjstring(1))
                            && font.getFont().getSize() == varargs.checkint(3)
                            && font.getFont().getStyle() == varargs.checkint(2))
            ){
                ArchWare.sendChatMessage(TextFormatting.RED + "SCRIPTAPI_EXCEPTION: This font is not registered. Please call create_new_instance");
                return NIL;
            }
            return LuaValue.valueOf(
                    REGISTERED_FONTS.stream().filter(
                            font -> font.getFont().getName().equals(varargs.checkjstring(1))
                                    && font.getFont().getSize() == varargs.checkint(3)
                                    && font.getFont().getStyle() == varargs.checkint(2)
                    ).findFirst().get().getStringWidth(varargs.checkjstring(4))
            );
        }

        @Override
        public String getName() {
            return "GetStringWidth";
        }
    }


    @Override
    public String getName() {
        return "customFont";
    }
}
