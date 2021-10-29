package fun.archware.impl.modules.player;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.StringValue;
import fun.archware.impl.events.EventPacketReceive;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.network.play.server.*;

/**
 * Created by 1 on 01.04.2021.
 */
public class Velocity extends Module {

    private StringValue mode = new StringValue("Mode", "VelocityMode", this, "Cancel", new String[]{"Vanilla", "Matrix"});

    public Velocity() {
        super("Velocity", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        setSuffix(mode.getValueString());
       if(this.mode.getValueString().equalsIgnoreCase("Matrix")){
           if(mc.player.hurtTime > 8){
               mc.player.onGround = true;
           }
       }
    }

    @EventTarget
    public void onReceivePacket(final EventPacketReceive event){
        if(mode.getValueString().equalsIgnoreCase("Vanilla")){
            if(event.getPacket() instanceof SPacketEntityVelocity || event.getPacket() instanceof SPacketExplosion){
                event.setCancelled(true);
            }
        }
    }


}
