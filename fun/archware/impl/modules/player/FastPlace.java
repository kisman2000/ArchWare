package fun.archware.impl.modules.player;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.client.Minecraft;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        if(mc.gameSettings.keyBindUseItem.isKeyDown()){
            Minecraft.getMinecraft().rightClickDelayTimer = 1;
        }
    }
}
