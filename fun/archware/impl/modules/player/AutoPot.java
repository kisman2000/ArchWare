package fun.archware.impl.modules.player;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.TimeUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;

import java.util.Objects;

public class AutoPot extends Module {
    public AutoPot() {
        super("AutoPot", Category.PLAYER);
    }
    ItemStack held;
    TimeUtil counter = new TimeUtil();
    @EventTarget
    public void onUpdate(final EventPreUpdate event){

        if (held == null && counter.hasReached(100))
            held = mc.player.getHeldItemMainhand();

        if (isPotionOnHotBar() && mc.player.onGround) {
            if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(1))))){
                event.setPitch(90);
                if(event.getPitch() == 90)
                throwPot(Potions.SPEED);
            }
            if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(5))))){
                event.setPitch(90);
                if(event.getPitch() == 90)
                    throwPot(Potions.STRENGTH);
            }
            if (!mc.player.isPotionActive((Objects.requireNonNull(Potion.getPotionById(12))))){
                event.setPitch(90);
                if(event.getPitch() == 90)
                    throwPot(Potions.FIRERES);
            }
            counter.reset();
        }
    }

    void throwPot(Potions potion) {
        int slot = getPotionSlot(potion);
        mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.playerController.updateController();
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
    }

    int getPotionSlot(Potions potion) {
        for(int i = 0; i < 9; ++i){
            if (this.isStackPotion(mc.player.inventory.getStackInSlot(i), potion))
                return i;
        }
        return -1;
    }

    boolean isPotionOnHotBar() {
        for(int i = 0; i < 9; ++i){
            if(isStackPotion(mc.player.inventory.getStackInSlot(i), Potions.STRENGTH)
                    || isStackPotion(mc.player.inventory.getStackInSlot(i), Potions.SPEED)
                    || isStackPotion(mc.player.inventory.getStackInSlot(i), Potions.FIRERES)){
                return true;
            }

        }
        return false;
    }

    boolean isStackPotion(ItemStack stack, Potions potion){
        if(stack == null)
            return false;

        Item item = stack.getItem();

        if(item == Items.SPLASH_POTION){
            int id = 5;

            switch (potion){
                case STRENGTH:
                    id = 5;
                    break;

                case SPEED:
                    id = 1;
                    break;

                case FIRERES:
                    id = 12;
                    break;

            }

            for(PotionEffect effect : PotionUtils.getEffectsFromStack(stack))
                if(effect.getPotion() == Potion.getPotionById(id))
                    return true;


        }

        return false;
    }
    enum Potions {
        STRENGTH, SPEED, FIRERES
    }
}
