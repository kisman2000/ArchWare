package fun.archware.impl.modules.player;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.TimeUtil;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;

public class ChestStealer extends Module {
    private final TimeUtil timer = new TimeUtil();
    private final NumericValue delay = new NumericValue("Delay", "ChestStealerDelay", this, 50, 5, 1000);
    public ChestStealer() {
        super("ChestStealer", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.player.openContainer instanceof ContainerChest){
            final ContainerChest chest = (ContainerChest)mc.player.openContainer;
            for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); ++i){
                final Slot slot = chest.inventorySlots.get(i);
                if(timer.hasReached((long)delay.getValueNumeric()) && Item.getIdFromItem(slot.getStack().getItem()) != 0){
                    mc.playerController.windowClick(chest.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
                    timer.reset();
                }
            }
        }
    }
}
