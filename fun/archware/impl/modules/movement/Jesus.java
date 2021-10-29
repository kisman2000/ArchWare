package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.MoveUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class Jesus extends Module {
    private StringValue mode = new StringValue("Mode", "JesusMode", this, "Matrix", new String[]{"Matrix", "Float"});
    private NumericValue motion = new NumericValue("Motion", "JesusMotion", this, 0.6f, 0.2f, 1.9f);
    private NumericValue motionY = new NumericValue("MotionY", "JesusMotionY", this, 0.6f, 0.2f, 0.8f);
    private NumericValue floatMotion = new NumericValue("Float speed", "jesusfloat", this, 9.9f, 0f, 15f);

    public Jesus() {
        super("Jesus", Category.MOVE);
    }

    @EventTarget
    public void onPreUpdate(final EventPreUpdate event){
        setSuffix(mode.getValueString());
        switch(mode.getValueString()){
            case "Matrix":
                if(mc.player.isInWater()){
                    mc.player.motionY = motionY.getValueNumeric();
                    MoveUtils.setSpeed(motion.getValueNumeric());
                }
                break;
            case "Float":
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.2, mc.player.posZ)).getBlock() == Block.getBlockById(9) && !mc.player.onGround) {
                        MoveUtils.setSpeed(floatMotion.getValueNumeric());
                    }
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0000001, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                        mc.player.motionX = 0.0;
                        mc.player.motionY = 0.06f;
                        mc.player.jumpMovementFactor = 0.01f;
                        mc.player.motionZ = 0.0;
                    }

                    break;
        }
    }
}
