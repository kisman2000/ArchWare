/*
 * Created with :heart: by katch.
 * (c) 4.22.2021
 */

package fun.archware.impl.modules.render;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPacketReceive;
import fun.archware.impl.events.EventRender3D;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.utils.BlockUtils;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Xray extends Module {
    private List<BlockPos> blocks = new ArrayList<>();
    private StringValue mode = new StringValue("Mode", "XrayMode", this, "Default", new String[]{"Default", "Xray-Bypass"});
    private NumericValue range = new NumericValue("Range", "XrayRange", this, 15, 5, 75);
    private long max;
    private BlockPos current;
    private final List<BlockPos> DETECTED_BLOCKS = new ArrayList<>();

    public Xray(){
        super("Xray", Category.RENDER);
    }

    @Override
    public void onEnable() {
        if(mode.getValueString().equalsIgnoreCase("Xray-Bypass")){
            DETECTED_BLOCKS.clear();
            blocks.clear();
            for(BlockPos block : BlockUtils.getBlocksInDistance((int)(range.getValueNumeric()))){
                if(mc.world.getBlockState(block).getBlock() != Blocks.AIR && mc.world.getBlockState(block).getBlock() != Blocks.STONE) blocks.add(block);
            }
            new Thread(() -> {
                for(final BlockPos block : blocks){
                    if(!isToggled()) return;
                    current = (current == null ? blocks.get(0) : blocks.get(blocks.indexOf(current) + 1));
                    mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, current, EnumFacing.NORTH));
                    try {
                        Thread.sleep(26);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                NotificationManager.addNotification(new Notification("Xray Bypass", "Done", NotificationType.OK));
            }).start();
        }
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        setSuffix(blocks.indexOf(current) + " / " + blocks.size());
    }

    @EventTarget
    public void onRender(final EventRender3D event){
        switch(mode.getValueString()){
            case "Xray-Bypass":
                for(final BlockPos block : DETECTED_BLOCKS){
                    final Block b = mc.world.getBlockState(block).getBlock();
                    Color color = Color.WHITE;
                    if(b == Blocks.DIAMOND_ORE){
                        color = Color.CYAN;
                    }else if(b == Blocks.GOLD_ORE){
                        color = Color.YELLOW;
                    }else if(b == Blocks.IRON_ORE){
                        color = Color.PINK;
                    }else if(b == Blocks.REDSTONE_ORE){
                        color = Color.RED;
                    }
                    double[] cords = {block.getX() - mc.getRenderManager().renderPosX, block.getY() - mc.getRenderManager().renderPosY, block.getZ() - mc.getRenderManager().renderPosZ};
                    RenderUtil.drawOutline(new AxisAlignedBB(cords[0], cords[1], cords[2], cords[0] + 1, cords[1] + 1, cords[2] + 1), 1.4f, color);
                }
                break;
            case "Default":
                for(BlockPos block : BlockUtils.getBlocksInDistance((int)(range.getValueNumeric()))){
                    final Block b = mc.world.getBlockState(block).getBlock();
                    if(isValidBlock(b)){
                        Color color = Color.WHITE;
                        if(b == Blocks.DIAMOND_ORE){
                            color = Color.CYAN;
                        }else if(b == Blocks.GOLD_ORE){
                            color = Color.YELLOW;
                        }else if(b == Blocks.IRON_ORE){
                            color = Color.PINK;
                        }else if(b == Blocks.REDSTONE_ORE){
                            color = Color.RED;
                        }
                        double[] cords = {block.getX() - mc.getRenderManager().renderPosX, block.getY() - mc.getRenderManager().renderPosY, block.getZ() - mc.getRenderManager().renderPosZ};
                        RenderUtil.drawOutline(new AxisAlignedBB(cords[0], cords[1], cords[2], cords[0] + 1, cords[1] + 1, cords[2] + 1), 1.7f, color);

                    }
                }
        }
    }

    @EventTarget
    public void onPacket(final EventPacketReceive event){
        if(mode.getValueString().equals("Xray-Bypass")){
            if(event.getPacket() instanceof SPacketBlockChange){
                final SPacketBlockChange packet = (SPacketBlockChange)event.getPacket();
                if(isValidBlock(packet.getBlockState().getBlock()) && !DETECTED_BLOCKS.contains(packet.getBlockPosition())){
                    DETECTED_BLOCKS.add(packet.getBlockPosition());
                    ArchWare.sendChatMessage("Detected " + packet.getBlockState().getBlock().getLocalizedName() + " at "
                            + packet.getBlockPosition().getX()
                            + ":" + packet.getBlockPosition().getY()
                            + ":" + packet.getBlockPosition().getZ());
                }
            }else if(event.getPacket() instanceof SPacketMultiBlockChange){
                final SPacketMultiBlockChange packet = (SPacketMultiBlockChange) event.getPacket();
                for(final SPacketMultiBlockChange.BlockUpdateData block : packet.getChangedBlocks()){
                    if(isValidBlock(block.getBlockState().getBlock()) && !DETECTED_BLOCKS.contains(block.getPos())){
                        DETECTED_BLOCKS.add(block.getPos());
                        ArchWare.sendChatMessage("Detected " + block.getBlockState().getBlock().getLocalizedName() + " at " +
                                block.getPos().getX()
                                + ":" + block.getPos().getY()
                                + ":" + block.getPos().getZ());
                    }
                }
            }
        }
    }

    private boolean isValidBlock(Block block){
        return block == Blocks.DIAMOND_ORE || block == Blocks.GOLD_ORE || block == Blocks.IRON_ORE || block == Blocks.REDSTONE_ORE;
    }
}
