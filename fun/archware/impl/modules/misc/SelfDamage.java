package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;

public class SelfDamage extends Module {
    private int jumps = 0;
    public SelfDamage() {
        super("SelfDamage", Category.MISC);
    }

    @Override
    public void onEnable() {
        jumps = 0;
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(jumps < 3){
            mc.timer.field_194147_b = 4;
            event.setOnGround(false);
        }
        if(mc.player.onGround){
            if(jumps < 3){
                mc.player.jump();
                ++jumps;
            }else{
                toggle();
            }
        }

    }
}
