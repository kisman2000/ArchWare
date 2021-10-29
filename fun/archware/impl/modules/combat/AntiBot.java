package fun.archware.impl.modules.combat;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPacketReceive;
import fun.archware.impl.events.EventPacketSend;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketSpawnPlayer;

import java.util.*;

public class AntiBot extends Module {
    StringValue mode = new StringValue("Mode", "AntiBotMode", this, "Invisible", new String[]{"Invisible", "Matrix", "Mineland", "Dev", "Reflex"});
    private UUID detectedEntity;
    public AntiBot() {
        super("AntiBot", Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        setSuffix(mode.getValueString());
        switch(mode.getValueString()){
            case "Invisible":
                for(final Entity e : mc.world.loadedEntityList){
                    if(e != mc.player && e.isInvisible()) mc.world.removeEntity(e);
                }
                break;
            case "Matrix":
                for(final Entity e : mc.world.loadedEntityList){
                    if(e.ticksExisted < 5 && e instanceof EntityOtherPlayerMP){
                       if(((EntityOtherPlayerMP) e).hurtTime > 0 && mc.player.getDistanceToEntity(e) <= 25 && mc.getConnection().getPlayerInfo(e.getUniqueID()).getResponseTime() != 0){
                           mc.world.removeEntity(e);
                       }
                    }
                }
                break;
            case "Mineland":
                System.out.println("a");
                break;
            case "Dev":
                if(detectedEntity != null){
                        NotificationManager.addNotification(new Notification("AntiBot", "Removed fake entity " + mc.world.getPlayerEntityByUUID(detectedEntity).getName(), NotificationType.OK));
                        mc.world.removeEntity(mc.world.getPlayerEntityByUUID(detectedEntity));
                    }
                break;
            case "Reflex":
                for(final Entity e : mc.world.loadedEntityList){
                    if(e.ticksExisted < 3 && ((EntityOtherPlayerMP)e).getHealth() > ((EntityOtherPlayerMP)e).getMaxHealth() || (e.ticksExisted < 3 && e.isInvisible())){
                        NotificationManager.addNotification(new Notification("AntiBot", "Removed fake entity " + e.getName(), NotificationType.OK));
                        mc.world.removeEntity(e);
                    }
                }
                break;
        }
    }

    @EventTarget
    public void onPacket(final EventPacketSend event){

    }

    @EventTarget
    public void onPacket(final EventPacketReceive event){
        if(event.getPacket() instanceof SPacketSpawnPlayer && mc.player.ticksExisted >= 9){
            if(((SPacketSpawnPlayer)event.getPacket()).getYaw() != 0 && ((SPacketSpawnPlayer)event.getPacket()).getPitch() != 0){
                detectedEntity = ((SPacketSpawnPlayer)event.getPacket()).getUniqueId();
                ArchWare.sendChatMessage(mc.world.getPlayerEntityByUUID(detectedEntity).rotationYaw + " pitch: " + mc.world.getPlayerEntityByUUID(detectedEntity).rotationPitch);
            }
        }
    }

    @Override
    public void onEnable(){
        super.onEnable();
    }
}
