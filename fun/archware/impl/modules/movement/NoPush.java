package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.NumericValue;
import fun.archware.impl.events.EventUpdate;

public class NoPush extends Module {
    public NoPush() {
        super("NoPush", Category.MOVE);
    }
    private NumericValue collreduction = new NumericValue("Collision Reduction","collreduction",this,1,0,1);
    @Override
    public void onDisable(){
        mc.player.entityCollisionReduction = 0f;
        super.onDisable();
    }
    @EventTarget
    public void onUpdate(final EventUpdate event){
        mc.player.entityCollisionReduction = 1f;
    }
}
