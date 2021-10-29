/*
 * Created with :heart: by katch.
 * (c) 4.25.2021
 */

package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.TimeUtil;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketEntityAction;

public class AutoTotem extends Module {
    NumericValue health = new NumericValue("Health", "AutoTotemHealth", this, 4, 2, 15);
    private TimeUtil timer = new TimeUtil();
    public AutoTotem() {
        super("AutoTotem", Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.player.getHealth() <= health.getValueNumeric() && Item.getIdFromItem(mc.player.inventoryContainer.getSlot(45).getStack().getItem()) != 449){
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.OPEN_INVENTORY));
            for(Slot slot : mc.player.inventoryContainer.inventorySlots){
                if(Item.getIdFromItem(slot.getStack().getItem()) == 449){
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot.slotNumber, 0, ClickType.PICKUP, mc.player);
                    if(timer.hasReached((long)(Math.random()*300))){
                        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, mc.player);
                        timer.reset();
                    }
                }
            }
        }
    }
}
