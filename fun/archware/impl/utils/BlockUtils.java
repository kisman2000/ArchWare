/*
 * Created with :heart: by katch.
 * (c) 4.22.2021
 */

package fun.archware.impl.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static List<BlockPos> getBlocksInDistance(int distance){
        List<BlockPos> blocks = new ArrayList<>();
        BlockPos.getAllInBox(new BlockPos(mc.player.posX - distance, mc.player.posY - distance, mc.player.posZ - distance), new BlockPos(mc.player.posX + distance, mc.player.posY + distance, mc.player.posZ + distance)).forEach(blocks::add);
        return blocks;
    }

    public float[] getRotations(BlockPos block){
        double x = (block.getX() - mc.player.posX);
        double z = (block.getZ() - mc.player.posZ);
        double y = (block.getY() - (mc.player.posY + mc.player.getEyeHeight()));
        double sqrted = MathHelper.sqrt(x*x + z*z);
        float yaw = (float)(Math.atan2(z,x) * 180.0 / Math.PI);
        float pitch = (float)-(Math.atan2(yaw, sqrted) * 180.0 / Math.PI);
        return new float[]{yaw, pitch};
    }
}
