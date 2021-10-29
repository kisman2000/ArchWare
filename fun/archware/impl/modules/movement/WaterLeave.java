package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class WaterLeave extends Module {
    private final NumericValue boost = new NumericValue("Boost", "WaterLeaveBoost", this, 12, 7, 18);
    public WaterLeave() {
        super("WaterLeave", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.world.getBlockState(new BlockPos(mc.player.posX,
                mc.player.posY - .90,
                mc.player.posZ)).getBlock() == Blocks.WATER && !mc.player.isInWater()){
            mc.player.motionY = boost.getValueNumeric();
            mc.player.onGround = true;
        }
    }
}
