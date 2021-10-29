package fun.archware.impl.modules.misc;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.BlockUtils;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.TimeUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;


public class SmartWClip extends Module {
    private final TimeUtil timeUtil = new TimeUtil();
    private final NumericValue range = new NumericValue("Range", "sosiuka1337", this, 25, 15, 125);
    private final NumericValue sleepTime = new NumericValue("Sleep time", "gayming132412", this, 2, 1, 5);
    private boolean inTeleportProcess;
    private BlockPos targetPosition;
    private double posY;
    public SmartWClip() {
        super("SmartWClip", Category.MISC);
    }

    @Override
    public void onEnable() {
        targetPosition = null;
        timeUtil.reset();
        inTeleportProcess = false;
        new Thread(() -> {
            for(final BlockPos blockPos : BlockUtils.getBlocksInDistance((int)range.getValueNumeric())){
                if(mc.world.getBlockState(blockPos).getBlock() == Blocks.WATER && mc.player.posY - blockPos.getY() >= 5){
                    if(mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ())).getBlock() != Blocks.AIR){
                        if(mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ())).getBlock().isFullBlock(mc.world.getBlockState(new BlockPos(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ()))))
                            continue;
                    }
                    ArchWare.sendChatMessage(String.format(TextFormatting.GREEN + "Safe position is: [%s:%s:%s]", blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                    targetPosition = new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    posY = mc.player.posY;
                    return;
                }
            }
            ArchWare.sendChatMessage(TextFormatting.RED + "Cannot find safe position :(");
            toggle();
        }).start();
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if((int)mc.player.posX == targetPosition.getX() && (int)mc.player.posZ == targetPosition.getZ() + 1){
            if(!inTeleportProcess){
                ArchWare.sendChatMessage(TextFormatting.YELLOW + "Teleporting you after " + (int)sleepTime.getValueNumeric() + "s...");
                timeUtil.reset();
                inTeleportProcess = true;
            }
            if(timeUtil.hasReached((long)sleepTime.getValueNumeric() * 1000)){
                mc.player.setPosition(mc.player.posX, targetPosition.getY(), mc.player.posZ);
                ArchWare.sendChatMessage(TextFormatting.GREEN + "Successfully!");
                toggle();
            }
        }else{
            if(inTeleportProcess){
                inTeleportProcess = false;
                ArchWare.sendChatMessage(TextFormatting.RED + "Cancelled.");
            }
        }
    }

    @EventTarget
    public void onRender(final EventRender3D event){
        if(targetPosition != null){
            final double[] cords = {targetPosition.getX() - mc.getRenderManager().renderPosX, posY - mc.getRenderManager().renderPosY, targetPosition.getZ() - mc.getRenderManager().renderPosZ};
            RenderUtil.drawOutline(new AxisAlignedBB(cords[0], cords[1], cords[2], cords[0] + 1, cords[1] + 2, cords[2] + 1), 1.6f, Color.WHITE);
        }
    }
}
