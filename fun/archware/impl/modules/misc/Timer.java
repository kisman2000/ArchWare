/*
 * Created with :heart: by katch.
 * (c) 4.25.2021
 */

package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;

public class Timer extends Module {
    private NumericValue timer = new NumericValue("Timer", "Timer", this, 0.3f, 0.01f, 2f);
    private final BooleanValue withTicks = new BooleanValue("ticks", "TimerTicks", this, false);
    private final NumericValue tickLength = new NumericValue("tick length", "TimerTickLength", this, 2, 3, 10);
    public Timer() {
        super("Timer", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(!withTicks.getValueBoolean()){
            mc.timer.field_194147_b = timer.getValueNumeric();
        }else{
            if(mc.player.ticksExisted % (int)tickLength.getValueNumeric() == 0){
                mc.timer.field_194147_b = timer.getValueNumeric();
            }
        }
    }
}
