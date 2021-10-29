package fun.archware.impl.modules.misc;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Disabler extends Module {
    public Disabler() {
        super("Disabler", Category.MISC);
    }

    @Override
    public void onEnable() {
        NotificationManager.addNotification(new Notification("Disabler", "Matrix got rekt", NotificationType.OK));
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(final EventPreUpdate event){
        if(mc.player.ticksExisted % 4 == 0){
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
}
