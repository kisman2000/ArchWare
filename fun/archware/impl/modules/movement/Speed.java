package fun.archware.impl.modules.movement;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventMoveRelative;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.MoveUtils;
import fun.archware.impl.utils.TimeUtil;

public class Speed extends Module {
    private StringValue mode = new StringValue("Mode", "SpeedMode", this, "Jump", new String[]{"Jump", "Matrix", "MatrixBoost", "MatrixOnGround", "Relative", "Dev"});
    private final NumericValue speed = new NumericValue("Matrix boost speed", "mboost", (Module)this, 0.1f, 0.05f, 0.5f);
    private final BooleanValue BoostSprint = new BooleanValue("Sprint boost", "sprint", (Module)this, false);
    private final TimeUtil timer = new TimeUtil();
    private boolean isSet;
    public Speed() {
        super("Speed", Category.MOVE);
    }
    private int jumps;

    @Override
    public void onEnable() {
        isSet = false;
        timer.reset();
       // mc.player.jump();
        super.onEnable();
    }
    @EventTarget
    public void onRelative(final EventMoveRelative event) {
        if(mode.getValueString().equals("Relative")){
            if(mc.player.onGround)
                mc.player.jump();
            if(mc.player.motionY > 0)
                mc.player.motionY -= 0.3;
            //ArchWare.sendChatMessage("" + mc.player.fallDistance);
                mc.player.onGround = false;
            }
        }
    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        setSuffix(mode.getValueString());
        if (!mode.getValueString().equals("MatrixBoost")) {
            mc.player.speedInAir = 0.02f;
        }
        switch(mode.getValueString()) {
            case "Jump":
                if(mc.player.onGround && mc.gameSettings.keyBindForward.isKeyDown()) mc.player.jump();
                break;
            case "Matrix":
                if(mc.player.onGround){
                    mc.player.jump();
                    mc.player.onGround = false;
                }
                if( mc.player.motionY > 0){
                    mc.player.motionY -= 1;
                }
                //MoveUtils.setSpeed(0.3);
                break;
            case "MatrixBoost":
                if (mc.player.onGround && (mc.player.movementInput.rightKeyDown || mc.player.movementInput.leftKeyDown || mc.player.movementInput.backKeyDown || mc.player.movementInput.forwardKeyDown)) {
                    mc.player.jump();
                    mc.player.jumpTicks = 0;
                }
                if (mc.player.motionY > 0.0 && !mc.player.isInWater()) {
                    mc.player.setSprinting(BoostSprint.getValueBoolean());
                }
                mc.player.speedInAir = speed.getValueNumeric();
                mc.player.onGround = false;
                break;
            case "MatrixOnGround":

                if(mc.player.onGround)
                    mc.player.jump();
                if(mc.player.motionY > 0)
                    mc.player.motionY -= mc.player.motionY;
                mc.player.onGround = false;

                break;
            case "Dev":
                if(mc.player.onGround){
                    mc.player.motionY = 0.2;
                    event.setOnGround(false);
                    MoveUtils.setSpeed(0.54);
                }
                mc.player.jumpTicks = 0;
                break;
        }
    }

    public void onDisable() {
        mc.player.speedInAir = 0.02f;
        super.onDisable();
    }
}
