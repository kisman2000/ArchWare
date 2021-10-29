/*
 * Created with :heart: by katch.
 * (c) 4.28.2021
 */

package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

public class CrystalAura extends Module {
    NumericValue range = new NumericValue("Range", "CrystalAuraRange", this, 3f, 0f, 6.1f);
    public CrystalAura() {
        super("CrystalAura", Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        for(Entity e : mc.world.loadedEntityList) {
            if(e instanceof EntityEnderCrystal && KillAura.target == null){
                if(mc.player.getDistanceToEntity(e) <= range.getValueNumeric()){
                    event.setPitch(RotationUtils.getRotation(e)[1]);
                    event.setYaw(RotationUtils.getRotation(e)[0]);
                    mc.player.rotationYawHead = event.getYaw();
                    mc.player.renderYawOffset = event.getYaw();
                    mc.player.rotationPitchHead = event.getPitch();
                    mc.getConnection().sendPacket(new CPacketUseEntity(e));
                    mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                }
            }
        }
    }
}
