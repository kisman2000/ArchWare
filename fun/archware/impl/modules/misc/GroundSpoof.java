package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.network.play.client.CPacketPlayer;

public class GroundSpoof extends Module {
    private final BooleanValue overrideDamage = new BooleanValue("Fals3R", "GroundSpoofOverrideDamage", this, true);
    public GroundSpoof() {
        super("GroundSpoof", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(event.isOnGround()){
            mc.player.setSprinting(false);
            event.setOnGround(false);
        }

    }

    @Override
    public void onDisable() {
        if(overrideDamage.getValueBoolean()) mc.getConnection().sendPacket(new CPacketPlayer.Position(0, 0, 0, false));
        super.onDisable();
    }
}
