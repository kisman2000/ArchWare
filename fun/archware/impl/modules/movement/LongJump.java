package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;

public class LongJump extends Module {
    private final NumericValue boost = new NumericValue("Boost", "LongJumpBoost", this, 0.07f, 0.03f, 1);
    public LongJump() {
        super("LongJump", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        setSuffix(String.format("%.2f", boost.getValueNumeric()));
        if(mc.player.hurtTime > 0 && mc.player.onGround){
            mc.player.jump();
        }
        if(!mc.player.onGround && mc.player.hurtTime > 0){
            mc.player.speedInAir = boost.getValueNumeric();
        }else{
            mc.player.speedInAir = 0.02f;
        }
    }

    @Override
    public void onDisable() {
        mc.player.speedInAir = 0.02f;
        super.onDisable();
    }
}
