package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.potion.Potion;

public class NoRender extends Module {
    private BooleanValue pumpkin = new BooleanValue("Pumpkin", "NoRenderPumpkin", this, true);
    private BooleanValue fire = new BooleanValue("Fire", "NoRenderFire", this, true);
    private BooleanValue portal = new BooleanValue("Portal", "NoRenderPortal", this, true);
    private final BooleanValue blindness = new BooleanValue("Blindness", "NoRenderBlindness", this, true);
    public NoRender() {
        super("NoRender", Category.RENDER);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(blindness.getValueBoolean()){
            if(mc.player.getActivePotionEffects().stream().anyMatch(potion -> Potion.getIdFromPotion(potion.getPotion()) == 15)){
                mc.player.removePotionEffect(Potion.getPotionById(15));
            }
        }
    }
}
