package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;

public class HighJump extends Module {
    public HighJump() {
        super("HighJump", Category.MOVE);
    }
    private NumericValue motionY = new NumericValue("Motion Y","highjumpmotiony",this,0.5f,0,5f);
    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.gameSettings.keyBindJump.pressed)
            mc.player.motionY = motionY.getValueNumeric();
    }
}
