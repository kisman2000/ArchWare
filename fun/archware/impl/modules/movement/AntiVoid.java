package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiVoid extends Module {
    private final StringValue mode = new StringValue("Mode", "AntiVoidMode", this, "Invalid pitch", new String[]{"Invalid pitch"});
    private double lastgroundposX;
    private double lastgroundposY;
    private double lastgroundposZ;
    public AntiVoid() {
        super("AntiVoid", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        setSuffix(mode.getValueString());
        switch(mode.getValueString()){
            case "Invalid pitch":
                if(mc.player.onGround){
                    lastgroundposX = mc.player.posX;
                    lastgroundposY = mc.player.posY;
                    lastgroundposZ = mc.player.posZ;
                }
                boolean isFalling = true;
                if(mc.player.fallDistance > 4){
                    for(int i = 0; i < 50; ++i){
                        if(mc.world.getBlockState(mc.player.getPosition().add(0, -i, 0)).getBlock() != Blocks.AIR){
                            isFalling = false;
                            break;
                        }
                    }
                    if(isFalling){
                        event.setOnGround(true);
                        event.setPitch(-91);
                        if(mc.player.ticksExisted % 15 == 0)
                            mc.getConnection().sendPacket(new CPacketPlayer.Position(lastgroundposX, lastgroundposY, lastgroundposZ, true));
                    }
                }
                break;
        }
    }
}
