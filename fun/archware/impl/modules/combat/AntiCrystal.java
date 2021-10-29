package fun.archware.impl.modules.combat;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.impl.utils.BlockUtils;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


public class AntiCrystal extends Module {
    private boolean noomlox;
    private final NumericValue range = new NumericValue("Range", "AntiCrystalRange", this, 3.8f, 3, 6);
    public AntiCrystal() {
        super("AntiCrystal", Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        setSuffix(String.format("%.1f", range.getValueNumeric()));
        for(final BlockPos block : BlockUtils.getBlocksInDistance((int)range.getValueNumeric())){
            if(mc.world.getBlockState(block).getBlock() == Blocks.OBSIDIAN && mc.world.getBlockState(block.add(0, 1, 0)).getBlock() == Blocks.AIR){
                ItemStack item;
                item = mc.player.inventory.mainInventory
                        .stream()
                        .filter(item2 -> mc.player.inventory.getSlotFor(item2) >= 0 && mc.player.inventory.getSlotFor(item2) <= 8)
                        .filter(item2 -> item2.getItem() instanceof ItemBlock)
                        .findAny()
                        .orElse(null);
                if(item != null && !item.getDisplayName().equals("Obsidian")){
                    final Vec3d eyesPos = new Vec3d(mc.player.posX + (Math.random() / 5), mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ + (Math.random() / 5));
                    final double diffX = new Vec3d(block.getX(), block.getY() - 1.2, block.getZ()).xCoord - eyesPos.xCoord;
                    final double diffY = new Vec3d(block.getX(), block.getY() - 1.2, block.getZ()).yCoord - eyesPos.yCoord;
                    final double diffZ = new Vec3d(block.getX(), block.getY() - 1.2, block.getZ()).zCoord - eyesPos.zCoord;
                    final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    float yaw = MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f);
                    float pitch = MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ))) - 20.0f);

                    final float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.4F;
                    final float gcd = f * f * f * 1.2F;
                    yaw -= yaw % gcd;
                    pitch -= pitch % gcd;
                    event.setYaw(yaw);
                    event.setPitch(pitch);

                    EnumFacing facing = EnumFacing.DOWN;


                    mc.getConnection().sendPacket(new CPacketHeldItemChange(mc.player.inventory.getSlotFor(item)));
                    mc.playerController.processRightClickBlockNoSync(mc.player, mc.world, block.add(0, 1, 0), facing, new Vec3d(block.getX(), block.getY(), block.getZ()), EnumHand.MAIN_HAND);
                    mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                    noomlox = true;
                }
            }
        }
        if(noomlox){
            mc.getConnection().sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        }
    }

}
