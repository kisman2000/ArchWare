package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;

public class FogColor extends Module {
    private final NumericValue red = new NumericValue("Red", "FogColorRed", this, 255, 0, 255);
    private final NumericValue green = new NumericValue("Green", "FogColorGreen", this, 255, 0, 255);
    private final NumericValue blue = new NumericValue("Blue", "FogColorBlue", this, 255, 0, 255);
    public FogColor() {
        super("FogColor", Category.RENDER);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        mc.entityRenderer.fogColorRed = red.getValueNumeric();
        mc.entityRenderer.fogColorGreen = green.getValueNumeric();
        mc.entityRenderer.fogColorBlue = blue.getValueNumeric();
    }
}
