/*
 * Created with :heart: by katch.
 * (c) 4.25.2021
 */

package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.utils.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;

public class ChestESP extends Module {
    public ChestESP() {
        super("ChestESP", Category.RENDER);
    }

    @EventTarget
    public void onRender(final EventRender3D event){
        for(TileEntity e : mc.world.loadedTileEntityList){
            if(e instanceof TileEntityChest){
                double x = e.getPos().getX() - mc.getRenderManager().renderPosX;
                double y = e.getPos().getY() - mc.getRenderManager().renderPosY;
                double z = e.getPos().getZ() - mc.getRenderManager().renderPosZ;
                RenderUtil.drawOutline(new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1), 1.6f, Color.ORANGE);
            }
        }
    }
}
