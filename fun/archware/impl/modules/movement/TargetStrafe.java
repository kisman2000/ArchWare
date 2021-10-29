package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventMoveRelative;
import fun.archware.impl.modules.combat.KillAura;
import fun.archware.impl.utils.MoveUtils;
import net.minecraft.entity.Entity;

public class TargetStrafe extends Module {
    private NumericValue range = new NumericValue("Range", "TargetStrafeRange", this, 3.6f, 0.1f, 7);
    private NumericValue motion = new NumericValue("Motion", "TargetStrafeMotion", this, 0.2f, 0.01f, 2);
    private BooleanValue Dynamic = new BooleanValue("Dynamic speed", "dynamicspeed", this, true);
    private BooleanValue damageboost = new BooleanValue("Damage Boost", "TargetStrafeDamageBoost", this, false);
    private Entity target;
    private double forward = 0;
    private double strafe = 1;

    private NumericValue damagemotion = new NumericValue("Damage Motion", "TargetStrafeDamageBoostMotion", this, 0.2f, 0.1f, 2);


    public TargetStrafe() {
        super("TargetStrafe", Category.MOVE);
    }
    @EventTarget
    public void onUpdate(final EventMoveRelative e){
            target = KillAura.target != null ? KillAura.target : null;
            double speed = Dynamic.getValueBoolean() ? motion.getValueNumeric() + Math.sqrt(mc.player.motionX*mc.player.motionX + mc.player.motionZ*mc.player.motionZ)/10 : motion.getValueNumeric();
            if(target != null){
            //if(mc.player.onGround) mc.player.jump();
                if( mc.player.hurtTime >= 6 && damageboost.getValueBoolean()){
                    speed += damagemotion.getValueNumeric();
                }
                if(getDistance(target) >= 0 && getDistance(target)  < range.getValueNumeric() +0.1)
                {
                    MoveUtils.setSpeed(0);
                    forward = 0;
                }
                else if(getDistance(target) >= 0   ) {
                    forward = 3;
                }
                if(mc.player.isCollidedHorizontally )
                    strafe = strafe == 1 ? -1 : 1;
                if(mc.gameSettings.keyBindRight.pressed) strafe = -1;
                if(mc.gameSettings.keyBindLeft.pressed) strafe = 1;
                if (forward != 0.0) {
                    if (forward > 0.0) {
                        forward = 1.0;
                    }
                    else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                e.yaw = KillAura.rightYawRotation;
                mc.player.motionX = (forward * speed * Math.cos(Math.toRadians(e.yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(e.yaw + 90.0f)));
                mc.player.motionZ = (forward * speed * Math.sin(Math.toRadians(e.yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(e.yaw + 90.0f)));
            }



    }
    private double getDistance(Entity target){
        double deltaX = mc.player.posX - target.posX;
        double deltaZ = mc.player.posZ - target.posZ;
        return Math.hypot(deltaX, deltaZ);
    }
}