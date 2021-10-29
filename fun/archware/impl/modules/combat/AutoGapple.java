package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.item.ItemAppleGold;

public class AutoGapple extends Module {
    private boolean isActive;
    NumericValue health = new NumericValue("Health", "AutoGappleHealth", this, 15, 5, 19);
    public AutoGapple() {
        super("AutoGapple", Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.player.getHealth() <= health.getValueNumeric()){
            if(!mc.player.isHandActive() && mc.player.getHeldItemOffhand().getItem() instanceof ItemAppleGold){
                isActive = true;
                mc.gameSettings.keyBindUseItem.pressed = true;
            }
        }else{
            if(isActive){
                mc.gameSettings.keyBindUseItem.pressed = false;
                isActive = false;
            }
        }
    }
}
