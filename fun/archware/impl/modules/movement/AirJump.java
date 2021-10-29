package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;

public class AirJump extends Module {
    public AirJump() {
        super("AirJump", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.gameSettings.keyBindJump.isKeyDown()){
            mc.player.onGround = true;
        }
    }
}
