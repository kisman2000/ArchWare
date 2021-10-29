/*
 * noom (c) 2021.
 */

package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;

/**
 * Created by 1 on 03.04.2021.
 */
public class AutoRespawn extends Module {
    private double[] cords;

    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate eventUpdate){
        if(mc.player.isDead){
            mc.player.respawnPlayer();
            NotificationManager.addNotification(new Notification("AutoRespawn", "Successfully respawn you", NotificationType.OK));
        }
    }
}
