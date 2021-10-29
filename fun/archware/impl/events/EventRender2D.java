package fun.archware.impl.events;

import fun.archware.base.event.Event;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

/**
 * Created by 1 on 01.04.2021.
 */
public class EventRender2D extends Event {
    private float partialTicks;

    public EventRender2D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public FontRenderer getFR(){
        return mc.fontRendererObj;
    }

    public int getStringWidth(String str){
       return getFR().getStringWidth(str);
    }


    public ScaledResolution getSR(){
        return new ScaledResolution(mc);
    }
}
