package fun.archware.impl.modules.misc;

import fun.archware.ArchWare;
import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.impl.events.EventPacketReceive;
import fun.archware.impl.utils.TimeUtil;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import fun.archware.notifications.NotificationType;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class FlagDetector extends Module {
    private int moduleCount;
    private final TimeUtil timeUtil = new TimeUtil();
    public FlagDetector() {
        super("FlagDetector", Category.MISC);
    }

    @EventTarget
    public void onPacket(final EventPacketReceive event){
        if(event.getPacket() instanceof SPacketPlayerPosLook){
            if(mc.player.ticksExisted < 4){
                NotificationManager.addNotification(new Notification("Flag detected", "New world detected", NotificationType.WARNING));
            }else{
                if(timeUtil.hasReached(4000)){
                    moduleCount = 0;
                    if(ArchWare.moduleManager.getModuleByName("Flight").isToggled()) {
                        ++moduleCount;
                        ArchWare.moduleManager.getModuleByName("Flight").toggle();
                    }
                    if(ArchWare.moduleManager.getModuleByName("Speed").isToggled()) {
                        ++moduleCount;
                        ArchWare.moduleManager.getModuleByName("Speed").toggle();
                    }
                    if(ArchWare.moduleManager.getModuleByName("Jesus").isToggled()) {
                        ++moduleCount;
                        ArchWare.moduleManager.getModuleByName("Jesus").toggle();
                    }
                    if(ArchWare.moduleManager.getModuleByName("TargetStrafe").isToggled()) {
                        ++moduleCount;
                        ArchWare.moduleManager.getModuleByName("TargetStrafe").toggle();
                    }
                    NotificationManager.addNotification(new Notification("Flag detected", moduleCount + " modules will be disabled due to lagback check.", NotificationType.WARNING));
                    timeUtil.reset();
                }
            }
        }
    }
}
