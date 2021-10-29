package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import jdk.Exported;
import net.minecraft.network.play.client.CPacketPlayer;

public class DamageReduce extends Module {
    public DamageReduce() {
        super("DamageReduce", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.player.fallDistance > 10){
            event.setOnGround(true);
            mc.player.connection.sendPacket(new CPacketPlayer(false));
            mc.player.fallDistance = 0;
        }
    }
}
