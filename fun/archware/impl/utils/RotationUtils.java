/*
 * noom (c) 2021.
 */

package fun.archware.impl.utils;

import javafx.animation.Interpolator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Created by 1 on 02.04.2021.
 */
public class RotationUtils {
    private static float prevYaw;
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static float[] getRotation(Entity entity){
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ );
        final double X = entity.getPositionVector().xCoord - eyesPos.xCoord + (Math.random() / 4);
        final double Y = entity.getPositionVector().yCoord + entity.getEyeHeight() - eyesPos.yCoord + (Math.random() / 4);
        final double Z = entity.getPositionVector().zCoord - eyesPos.zCoord + (Math.random() / 4);
        final double XZ = Math.sqrt(X * X + Z * Z);
        float yaw = MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(Z, X)) - 90.0f );
        float pitch = MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(Y, XZ))) + 5);

        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float gcd = (f * f * f) * 10;
        yaw -= yaw % gcd;
        pitch -= pitch % gcd;

        return new float[]{MathHelper.clamp(yaw,-360,360), MathHelper.clamp(pitch,-90,90)};
    }

    public static float[] getVisualRotations(final Entity entity){
        final Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
        final double diffX = entity.getPositionVector().xCoord - eyesPos.xCoord;
        final double diffY = entity.getPositionVector().yCoord - eyesPos.yCoord;
        final double diffZ = entity.getPositionVector().zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw;
        if(mc.player.ticksExisted % 8 == 0){
            yaw = MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f) + (float)(Math.random()*5);
            prevYaw = yaw;

        }else{
            yaw = prevYaw;
        }
        final double v = -Math.toDegrees(Math.atan2(diffY, diffXZ));
        float pitch = MathHelper.wrapDegrees((float) v - 20.0f);
        if(mc.player.onGround){
            pitch = -MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - 70);
        }else{
            pitch = 90;
        }

        return new float[]{yaw, pitch};
    }

}
