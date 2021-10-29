package fun.archware.impl.modules.render;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import net.minecraft.util.text.TextFormatting;

public class DeathCoordinates extends Module {

    public DeathCoordinates() {
        super("DeathCoordinates", Category.RENDER);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.player.isDead){
            ArchWare.sendChatMessage(TextFormatting.RED + "Your death coordinates: X:" + (int) + mc.player.posX + " Y: " + (int)mc.player.posY + " Z: " + (int)mc.player.posZ);
        }
    }
}
