package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;

public class Spider extends Module {
    public Spider() {
        super("Spider", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if (mc.player.isCollidedHorizontally) {
            if(mc.player.onGround){
                mc.player.motionY = .2;
                event.setOnGround(true);
            }else{
                if(mc.player.fallDistance > 0.01){
                    mc.player.motionY = .2;
                    event.setOnGround(true);
                }
            }
        }
    }
}
