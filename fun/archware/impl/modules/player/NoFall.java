package fun.archware.impl.modules.player;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.MoveUtils;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class NoFall extends Module {
    private boolean isInAir;
    public NoFall() {
        super("NoFall", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.player.fallDistance > 3 || isInAir){
            event.setOnGround(false);
            isInAir = true;
        }
        if(isInAir && mc.player.onGround){
            event.setY(0);
            event.setY(0);
            event.setZ(0);
            event.setOnGround(false);
            isInAir = false;
            event.setOnGround(true);
        }
    }
}
