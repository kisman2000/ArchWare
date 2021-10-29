package fun.archware.impl.modules.player;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.impl.events.EventPacketSend;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.*;

public class NoInteract extends Module {
    private BooleanValue craft = new BooleanValue("Crafting table", "NoInteractCraftingTable", this, true);
    private BooleanValue furnace = new BooleanValue("Furnace", "NoInteractFurnace", this, true);
    private final BooleanValue armorStand = new BooleanValue("Armor Stand", "NoInteractArmorStand", this, true);
    public NoInteract() {
        super("NoInteract", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
    }

    @EventTarget
    public void onPacket(final EventPacketSend event){
        if(event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock){
            final Block block = mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
            if((block == Blocks.CRAFTING_TABLE && craft.getValueBoolean()) ||
                    (block == Blocks.FURNACE && furnace.getValueBoolean()) ||
                    (mc.objectMouseOver.entityHit != null && mc.objectMouseOver.entityHit instanceof EntityArmorStand && armorStand.getValueBoolean())){
                event.setCancelled(true);
            }

        }
    }


}
