/*
 * Created with :heart: by katch.
 * (c) 4.24.2021
 */

package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPacketSend;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.MoveUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.*;
import net.minecraft.world.GameType;

public class Freecam extends Module {
    private GameType prevGameType;
    //private final NumericValue VerticalSpeed = new NumericValue("Vertical Speed", "FreecamVerticalSpeed", this, 0.5f, 0.5f, 5f);

    private final NumericValue speed = new NumericValue("Speed", "FreecamSpeed", this, 0.5f, 0.5f, 5f);

    private EntityOtherPlayerMP entity;
    public Freecam() {
        super("Freecam", Category.MISC);
    }

    @Override
    public void onEnable() {
        try{
            entity = new EntityOtherPlayerMP(mc.world, mc.player.getGameProfile());
            entity.posX = mc.player.posX;
            entity.posY = mc.player.posY;
            entity.posZ = mc.player.posZ;
            entity.rotationYaw = mc.player.rotationYaw;
            entity.rotationPitch = mc.player.rotationPitch;
            mc.world.addEntityToWorld(-1, entity);
            prevGameType = mc.playerController.getCurrentGameType();
            mc.playerController.setGameType(GameType.SPECTATOR);

        }catch(NullPointerException ignored){}
        super.onEnable();
    }

    @Override
    public void onDisable() {
        mc.player.setVelocity(0,0,0);
        mc.player.setPositionAndRotation(entity.posX, entity.posY, entity.posZ,entity.rotationYaw,entity.rotationPitch);
        mc.world.removeEntity(entity);
        mc.playerController.setGameType(prevGameType);

        super.onDisable();
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        //mc.getConnection().sendPacket(new CPacketInput(0,0,false,false));
        if(mc.gameSettings.keyBindJump.pressed) mc.player.motionY += speed.getValueNumeric()/2;
        if(mc.gameSettings.keyBindSneak.pressed) mc.player.motionY -= speed.getValueNumeric()/2;
        MoveUtils.setSpeed(speed.getValueNumeric());
    }


    @EventTarget
    public void onPacket(final EventPacketSend event){
        if(mc.player != null){
            if(!(event.getPacket() instanceof CPacketKeepAlive)) event.setCancelled(true);
        }
    }
}