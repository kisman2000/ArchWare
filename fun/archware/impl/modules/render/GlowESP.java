package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class GlowESP extends Module {
    public GlowESP() {
        super("GlowESP", Category.RENDER);
    }

    @EventTarget
    public void onUpdate(final EventUpdate event){
        for(final Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityPlayer){
                e.setGlowing(true);
            }
        }
    }

    @Override
    public void onDisable() {
        for(final Entity e : mc.world.loadedEntityList){
            if(e instanceof EntityPlayer && e.isGlowing()){
                e.setGlowing(false);
            }
        }
        super.onDisable();
    }
}
