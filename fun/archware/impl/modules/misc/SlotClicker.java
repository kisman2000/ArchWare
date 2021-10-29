/*
 * Created with :heart: by katch.
 * (c) 4.22.2021
 */

package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;

public class SlotClicker extends Module {
    private NumericValue slot = new NumericValue("Slot", "SlotClicker", this, 0, 0, 37);
    public SlotClicker() {
        super("SlotClicker", Category.MISC);
    }

    @EventTarget
    public void onPreUpdate(final EventPreUpdate event){
        if(mc.currentScreen instanceof GuiContainer){
            mc.playerController.windowClick(mc.player.openContainer.windowId, (int)(slot.getValueNumeric()), 0, ClickType.PICKUP, mc.player);
        }
    }
}
