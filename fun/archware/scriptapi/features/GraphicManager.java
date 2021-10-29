package fun.archware.scriptapi.features;

import fun.archware.impl.utils.RenderUtil;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

import java.awt.*;

public class GraphicManager implements Feature {
    @Override
    public String getName() {
        return "draw";
    }

    public static class DrawString extends VarArgFunction implements Feature {
        @Override
        public Varargs invoke(Varargs varargs) {
            mc.fontRendererObj.drawString(varargs.checkjstring(1), varargs.checkint(2), varargs.checkint(3), varargs.checkint(4));
            return NIL;
        }

        @Override
        public String getName() {
            return "drawString";
        }
    }
    public static class DrawRect extends VarArgFunction implements Feature{
        @Override
        public Varargs invoke(Varargs varargs) {
            RenderUtil.drawRect(varargs.checkdouble(1),varargs.checkdouble(2),varargs.checkdouble(3),varargs.checkdouble(4),new Color(varargs.checkint(5),varargs.checkint(6),varargs.checkint(7),varargs.checkint(8)).hashCode());
            return NIL;
        }

        @Override
        public String getName() {
            return "drawRect";
        }
    }
}
