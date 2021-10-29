package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventRender2D;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class CoolFeatureBtw extends Module {
    public CoolFeatureBtw() {
        super("CoolFeatureBtw", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender2D event){
        final ScaledResolution sr = new ScaledResolution(mc);
        RenderUtil.drawRect(0, sr.getScaledHeight() / 2 - 0.5, sr.getScaledWidth(), 0.5, Color.BLACK.hashCode());
        RenderUtil.drawRect(sr.getScaledWidth() / 2 - 0.5, 0, 0.5, sr.getScaledHeight(), Color.BLACK.hashCode());
    }
}
