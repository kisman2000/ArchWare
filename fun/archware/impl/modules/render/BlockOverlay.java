package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;

public class BlockOverlay extends Module {
    public BlockOverlay() {
        super("BlockOverlay", Category.RENDER);
    }
    private NumericValue red = new NumericValue("Red", "BORed", this, 255, 0, 255);
    private NumericValue green = new NumericValue("Green", "BOGreen", this, 255, 0, 255);
    private NumericValue blue = new NumericValue("Blue", "BOBlue", this, 255, 0, 255);
    private NumericValue alpha = new NumericValue("Alpha", "BOAlpha", this, 255, 0, 255);

    @EventTarget
    public void onRender(final EventRender3D event){
        if(mc.objectMouseOver.getBlockPos() != null && mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() != Blocks.AIR && mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock().isFullBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()))){
            double[] cords = {mc.objectMouseOver.getBlockPos().getX() - mc.getRenderManager().renderPosX, mc.objectMouseOver.getBlockPos().getY() - mc.getRenderManager().renderPosY, mc.objectMouseOver.getBlockPos().getZ() - mc.getRenderManager().renderPosZ};
            RenderUtil.drawOutline(new AxisAlignedBB(cords[0], cords[1], cords[2], cords[0] + 1, cords[1] + 1, cords[2] + 1), 1.7f, new Color((int)red.getValueNumeric(), (int)green.getValueNumeric(), (int)blue.getValueNumeric(), (int)alpha.getValueNumeric()));
        }
    }
}
