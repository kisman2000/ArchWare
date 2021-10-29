/*
 * Created with :heart: by katch.
 * (c) 4.22.2021
 */

package fun.archware.impl.modules.render;

import fun.archware.base.event.EventTarget;
import fun.archware.base.module.Category;
import fun.archware.base.module.Module;
import fun.archware.base.setting.BooleanValue;
import fun.archware.impl.events.EventTick;
import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.font.CustomFontRenderer;
import fun.archware.impl.utils.font.FontUtils;
import fun.archware.notifications.Notification;
import fun.archware.notifications.NotificationManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class Notifications extends Module {
    private BooleanValue modules = new BooleanValue("Modules activity", "NotificationsModules", this, true);
    private final CustomFontRenderer icons = new CustomFontRenderer(FontUtils.getFontFromTTF(new ResourceLocation("notifications.ttf"), 45, Font.PLAIN), true, true);
    private final CustomFontRenderer font = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 15), true, true);

    public Notifications() {
        super("Notifications", Category.RENDER);
        setShown(false);
    }

    @EventTarget
    public void onRender(final EventTick event){
        final ScaledResolution sr = new ScaledResolution(mc);
        for(int i = 0; i < NotificationManager.getQueue().size(); i++) {
            final Notification notification = NotificationManager.getQueue().get(i);
            Color color = null;
            switch(notification.getNotificationType()){
                case OK:
                    color = Color.GREEN;
                    break;
                case WARNING:
                    color = Color.ORANGE;
                    break;
                case ERROR:
                    color = Color.RED;
                    break;
            }
            notification.setLastTickPosY(notification.getPosY());
            notification.setLastTickPosX(notification.getPosX());
            Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(notification.getDescription()) - 6 - notification.getPosX(), sr.getScaledHeight()  - notification.getPosY(), sr.getScaledWidth() - 3 - notification.getPosX(), sr.getScaledHeight() + 12 - notification.getPosY(), new Color(15, 15, 15).hashCode());
            font.drawString(notification.getDescription(), sr.getScaledWidth() - font.getStringWidth(notification.getDescription()) - 4.5 - notification.getPosX(), sr.getScaledHeight() + 4 - notification.getPosY(), -1);
            Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(notification.getDescription()) - 6 - notification.getPosX(), sr.getScaledHeight() - notification.getPosY() + 10, sr.getScaledWidth() - font.getStringWidth(notification.getDescription()) - 6 + notification.getWidth() - notification.getPosX(), sr.getScaledHeight() + 12 - notification.getPosY(), RenderUtil.fade(color,1,1).hashCode());
        }
    }

    @EventTarget
    public void onTick(final EventTick event){
        for(int i = 0; i < NotificationManager.getQueue().size(); ++i){
            final Notification notification = NotificationManager.getQueue().get(i);
            if(!notification.isPassed()){
                if(notification.getPosY() >= 25){
                    notification.setPassed(true);
                }
                notification.setPosY((notification.getLastTickPosY() + (notification.getPosY() + 2 - notification.getLastTickPosY()) * event.getPartialTicks()));
            }else{
                if(new ScaledResolution(mc).getScaledWidth() - 3 <= new ScaledResolution(mc).getScaledWidth() - font.getStringWidth(notification.getDescription()) - 6 + notification.getWidth()){
                    if(new ScaledResolution(mc).getScaledWidth() + notification.getPosX() > new ScaledResolution(mc).getScaledWidth() - font.getStringWidth(notification.getDescription()) - 6){
                        notification.setPosX((notification.getLastTickPosX() + (notification.getPosX() - 2 - notification.getLastTickPosX()) * event.getPartialTicks()));
                    }else{
                        NotificationManager.getQueue().remove(notification);
                    }
                }else{
                    notification.setWidth(notification.getWidth() + 0.3);
                }
            }
        }
    }
}