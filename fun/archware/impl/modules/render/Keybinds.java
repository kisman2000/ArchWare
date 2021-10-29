package fun.archware.impl.modules.render;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventRender2D;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.font.CustomFontRenderer;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Keybinds extends Module {
    private final NumericValue x = new NumericValue("X", "KeybindsX", this, 1, 1, Float.MAX_VALUE);
    private final NumericValue y = new NumericValue("Y", "KeybindsY", this, 236, 1, Float.MAX_VALUE);
    public static double posX, posY, posX2, posY2;
    public static boolean isDragging;
    private static final CustomFontRenderer font = new CustomFontRenderer(new Font("Tahoma", Font.PLAIN, 15), true, true);
    public Keybinds() {
        super("Keybinds", Category.RENDER);
        x.invisible();
        y.invisible();
    }

    @EventTarget
    public void onRender(final EventRender2D event){
        posX = x.getValueNumeric();
        posY = y.getValueNumeric();
        //RenderUtil.drawCoolRect("keybinds", 1, 65, 35, 0, -1);
        RenderUtil.drawCoolRect("keybinds", x.getValueNumeric(), y.getValueNumeric() - 11, 85);
        RenderUtil.drawRect(x.getValueNumeric(), y.getValueNumeric(), 85, 10 *
                ArchWare.moduleManager.modules.stream().filter(m -> m.isToggled() && m.getKey() != 0).count(), new Color(0, 0, 0, 105).hashCode());
        double posY = y.getValueNumeric() + 3;
        for(final Module m : ArchWare.moduleManager.modules){
            if(m.isToggled() && m.getKey() != 0){
                font.drawString(m.getName().toLowerCase(), x.getValueNumeric() + 1, posY, -1);
                font.drawString(Keyboard.getKeyName(m.getKey()), x.getValueNumeric() + 82 - font.getStringWidth(Keyboard.getKeyName(m.getKey())), posY, -1);
                posY += 10;
            }
        }
    }

}
