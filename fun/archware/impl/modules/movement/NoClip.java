package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;

public class NoClip extends Module {
    public NoClip() {
        super("NoClip", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        mc.player.motionY = 0;
        if(mc.gameSettings.keyBindJump.pressed) mc.player.motionY += 0.1;
        if(mc.gameSettings.keyBindSneak.pressed) mc.player.motionY -= 0.1;
        mc.player.onGround = true;
    }
}
