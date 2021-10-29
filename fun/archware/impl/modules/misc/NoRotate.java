package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPacketReceive;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", Category.MISC);
    }

    @EventTarget
    public void onPacket(final EventPacketReceive event){
        if(event.getPacket() instanceof SPacketPlayerPosLook){
            final Packet packet = event.getPacket();
            ((SPacketPlayerPosLook)packet).yaw = mc.player.rotationYaw;
            ((SPacketPlayerPosLook)packet).pitch = mc.player.rotationPitch;
        }
    }

}
