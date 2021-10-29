package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;

public class Step extends Module {
    public Step() {
        super("Step", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){

    }
}
