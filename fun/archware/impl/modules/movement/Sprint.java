/*
 * noom (c) 2021.
 */

/*
 * noom (c) 2021.
 */

package fun.archware.impl.modules.movement;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventUpdate;
import fun.archware.impl.modules.combat.KillAura;

/**
 * Created by 1 on 02.04.2021.
 */
public class Sprint extends Module {

    public Sprint() {
        super("Sprint", Category.MOVE);
    }

    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate){
        mc.player.setSprinting(!mc.player.isCollidedHorizontally && mc.gameSettings.keyBindForward.isKeyDown() && KillAura.xd);
    }
}
