package fun.archware.impl.modules.misc;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPreUpdate;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;
import net.minecraft.network.play.client.CPacketEntityAction;

public class CrimWalk extends Module {
    public CrimWalk(){
        super("CrimWalk", Category.MISC);
    }

    @Override
    public void onEnable() {
        NotificationManager.addNotification(new Notification("ArchWare airlines", "Haha akrien killaura go brr", NotificationType.OK));
        super.onEnable();
    }

    @EventTarget
    public void onPreUpdate(final EventPreUpdate event){
        mc.player.setSneaking(!mc.player.isSneaking()); // idk how but its working lol. Say bye bye for neverhook killaura
    }
}
