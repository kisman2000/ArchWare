package fun.archware.impl.utils;

import net.minecraft.client.Minecraft;

public class MoveUtils {
    public static void setSpeed(double speed){
        double forward = Minecraft.getMinecraft().player.movementInput.field_192832_b;
        double strafe = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            Minecraft.getMinecraft().player.motionX = (0.0);
            Minecraft.getMinecraft().player.motionZ = (0.0);
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            Minecraft.getMinecraft().player.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            Minecraft.getMinecraft().player.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
    public static void unsafeSetSpeed(double speed){
        double strafe = Minecraft.getMinecraft().player.movementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().player.rotationYaw;
        if (strafe == 0.0) {
            Minecraft.getMinecraft().player.motionX = (0.0);
            Minecraft.getMinecraft().player.motionZ = (0.0);
        }

            Minecraft.getMinecraft().player.motionX = (speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            Minecraft.getMinecraft().player.motionZ = (speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }

