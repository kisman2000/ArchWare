package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventMiddleClick;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;

public class KeyPearl extends Module {
    public KeyPearl() {
        super("KeyPearl", Category.COMBAT);
    }

    @EventTarget
    public void onMiddleClick(final EventMiddleClick event){
        for(int i = 0; i < 9; ++i){
            final ItemStack slot = mc.player.inventory.getStackInSlot(i);
            if(Item.getIdFromItem(slot.getItem()) == 368){
                mc.getConnection().sendPacket(new CPacketHeldItemChange(mc.player.inventory.getSlotFor(slot)));
                mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
                mc.getConnection().sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
            }
        }
    }
}
