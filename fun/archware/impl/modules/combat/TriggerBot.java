/*
 * Created with :heart: by katch.
 * (c) 4.26.2021
 */

package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;

public class TriggerBot extends Module {
    public TriggerBot() {
        super("TriggerBot", Category.COMBAT);
    }
    private final NumericValue range = new NumericValue("Range","TriggerBotRange", this, 3.5f,0, 6);
    private final BooleanValue critical = new BooleanValue("Criticals","nigg",this,false);
    private final NumericValue fallDistance = new NumericValue("Fall distance","TriggerBotFallDistance", this, 0.01f,0, 0.09f);

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityLivingBase && mc.player.getDistanceToEntity(mc.objectMouseOver.entityHit) <= range.getValueNumeric()){
            if (!critical.getValueBoolean()) {
                if (mc.player.getCooledAttackStrength(0) == 1) {
                    mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }else{
                if (mc.player.getCooledAttackStrength(0) > 0.9 && mc.player.fallDistance > fallDistance.getValueNumeric()) {
                    mc.playerController.attackEntity(mc.player, mc.objectMouseOver.entityHit);
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            }

        }
    }
}
