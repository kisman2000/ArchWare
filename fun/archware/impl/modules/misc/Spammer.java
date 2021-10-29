package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.SpammerUtils;
import fun.archware.impl.utils.TimeUtil;

/**
 * Created by 1 on 07.04.2021.
 */
public class Spammer extends Module{

    private NumericValue delay = new NumericValue("Delay", "SpammerDelay", this, 1000, 500, 10000);
    private TimeUtil timer = new TimeUtil();

    public Spammer() {
        super("Spammer", Category.MISC);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(timer.hasReached(delay.getValueNumeric())){
            mc.player.sendChatMessage(SpammerUtils.getMessage());
            timer.reset();
        }
    }

}