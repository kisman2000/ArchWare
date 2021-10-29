package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;

public class CustomTime extends Module {
    private final NumericValue time = new NumericValue("Time", "CustomTime", this, 12000, 1000, 23992);
    private final BooleanValue cycle = new BooleanValue("Cycle", "CustomTimeCycle", this, false);
    private final NumericValue cycleSpeed = new NumericValue("Cycle speed", "CustomTimeCycleSpeed", this, 55, 5, 250);
    private final BooleanValue rain = new BooleanValue("Rain", "CustomTime", this, false);
    public CustomTime() {
        super("CustomTime", Category.RENDER);
    }

    @EventTarget
    public void onTick(final EventUpdate event){
        mc.world.setRainStrength(rain.getValueBoolean() ? 1 : 0);
        if(cycle.getValueBoolean()){
            time.setValueNumeric(mc.world.getWorldTime() + (long)cycleSpeed.getValueNumeric());
            if(time.getValueNumeric() >= time.getMaxValue()){
                time.setValueNumeric(time.getMinValue());
            }
        }
    }
}
