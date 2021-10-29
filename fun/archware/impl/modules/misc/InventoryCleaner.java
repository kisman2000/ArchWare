package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.*;

public class InventoryCleaner extends Module {
    public InventoryCleaner() {
        super("InventoryCleaner", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.currentScreen instanceof GuiInventory){
            for(int i = 0; i < mc.player.inventoryContainer.inventoryItemStacks.size(); ++i){
                final ItemStack item = mc.player.inventoryContainer.inventoryItemStacks.get(i);
                if(!(item.getItem() instanceof ItemBlock) && !(item.getItem() instanceof ItemFood) && !(item.getItem() instanceof ItemArmor) && !(item.getItem() instanceof ItemTool)){
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, i, 0, ClickType.THROW, mc.player);
                    continue;
                }
            }
        }
    }
}
