package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventMoveRelative;
import fun.archware.impl.events.EventPacketReceive;
import fun.archware.impl.utils.MoveUtils;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.MathHelper;

public class Flight extends Module {
    private final NumericValue speed = new NumericValue("Speed", "FlySpeed", this, 1.7f, 1.5f, 5.3f);
    private final StringValue mode = new StringValue("Mode", "FlightMode", this, "Motion", new String[]{"Motion", "Mineland", "Funcraft", "Meme"});
    private final BooleanValue autojump = new BooleanValue("Auto Jump", "FlightAutoJump", this, true);
    private boolean isJump;
    public Flight() {
        super("Flight", Category.MOVE);
    }

    @EventTarget
    public void onPacket(final EventPacketReceive e){
        if(e.getPacket() instanceof SPacketPlayerPosLook){
            isJump = false;
        }
    }
    @EventTarget
    public void onPreUpdate(final EventMoveRelative event){
        setSuffix(mode.getValueString());
        if(autojump.getValueBoolean() && mc.player.onGround) mc.player.jump();
        switch (mode.getValueString()) {
            case "Motion":
                mc.player.motionY = 0;
                if(mc.gameSettings.keyBindForward.pressed && !mc.player.onGround) {
                    double f = Math.toRadians(mc.player.rotationYaw);
                    mc.player.motionX -=(MathHelper.sin((float) f) * 0.2F);
                    mc.player.motionZ +=(MathHelper.cos((float) f) * 0.2F);
                }
                if(mc.gameSettings.keyBindJump.pressed)mc.player.motionY = 0.5;
                if(mc.gameSettings.keyBindSneak.pressed)mc.player.motionY -= 0.5;
                break;
            case "Mineland":
                if(mc.gameSettings.keyBindForward.isKeyDown()){
                    mc.player.motionY = -.3;

                }
                break;
            case "Funcraft":
                mc.player.motionY = 0;
                if(mc.player.onGround)
                    mc.player.jump();
                else {
                    mc.player.motionY = -0.01;
                    event.friction = event.friction * 12f;
                }
                mc.player.onGround = true;
                break;
            case "Meme":
                if(mc.player.hurtTime == 8){
                    NotificationManager.addNotification(new Notification("Meme", "You can fly for 5 seconds", NotificationType.OK));
                    mc.player.motionY = 0.2;
                    //MoveUtils.setSpeed(2);
                }
                break;
        }
    }
}