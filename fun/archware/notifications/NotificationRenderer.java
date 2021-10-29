package fun.archware.notifications;

import fun.archware.impl.utils.RenderUtil;
import fun.archware.impl.utils.font.CustomFontRenderer;
import fun.archware.impl.utils.font.FontUtils;
import javafx.animation.Interpolator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import static org.lwjgl.opengl.GL11.*;

public class NotificationRenderer {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final CustomFontRenderer font = new CustomFontRenderer(new Font("Arial", Font.PLAIN, 15), true, true);
    private static final CustomFontRenderer icons = new CustomFontRenderer(FontUtils.getFontFromTTF(new ResourceLocation("fonts/notifications.ttf"), 28, Font.PLAIN), true, true);
    public static void render(final float partialTicks, final ScaledResolution scaledResolution){
        //if(NotificationManager.getQueue().isEmpty()) NotificationManager.addNotification(new Notification("Dolbaeb", "Sosi hui pidoras ebaniy mat tvoyu ebal", NotificationType.OK));
        for(int i = 0; i < NotificationManager.getQueue().size(); ++i){
            final Notification notification = NotificationManager.getQueue().get(i);
            Color color;
            switch(notification.getNotificationType()){
                case WARNING:
                    color = Color.ORANGE;
                    break;
                case ERROR:
                    color = Color.RED;
                    break;
                default:
                    color = Color.GREEN;
            }
            notification.setPosY((float)Interpolator.EASE_OUT.interpolate(notification.getPosY(), scaledResolution.getScaledHeight() - 5 - (NotificationManager.getQueue().size() == 1 ? 23 : (23 * NotificationManager.getQueue().indexOf(notification))), (0.1 * 100) / Minecraft.getDebugFPS()));
            if(notification.getWidth() < font.getStringWidth(notification.getDescription()) + 32){
                notification.setScissorWidth((float)Interpolator.EASE_OUT.interpolate(notification.getScissorWidth(), -font.getStringWidth(notification.getDescription()) - 32, (0.09*100)/ Minecraft.getDebugFPS()));
            }
            glPushMatrix();
            glEnable(GL_SCISSOR_TEST);
            RenderUtil.prepareScissorBox(scaledResolution.getScaledWidth() + (int)notification.getScissorWidth(), (int)notification.getPosY(), scaledResolution.getScaledWidth(), (int)notification.getPosY() + 22);
            RenderUtil.drawRect(scaledResolution.getScaledWidth(), notification.getPosY(), notification.getScissorWidth(), 22, new Color(7, 7, 7, 225).hashCode());
            RenderUtil.drawRect(scaledResolution.getScaledWidth() + notification.getScissorWidth(), notification.getPosY() + 20.5, notification.getWidth(), 1, color.hashCode());
            icons.drawString(String.valueOf(notification.getNotificationType().getChar()), scaledResolution.getScaledWidth() + (-font.getStringWidth(notification.getDescription()) - 58) + 30, notification.getPosY() + 6.5, -1);
            font.drawString(notification.getTitle(), scaledResolution.getScaledWidth() + (-font.getStringWidth(notification.getDescription()) - 62) + 50, notification.getPosY() + 4, -1);
            font.drawString(notification.getDescription(), scaledResolution.getScaledWidth() + (-font.getStringWidth(notification.getDescription()) - 62) + 50, notification.getPosY() + 13, -1);
            glDisable(GL_SCISSOR_TEST);
            glPopMatrix();
            notification.setWidth(notification.getWidth() + (0.25*100)/ Minecraft.getDebugFPS());
            if(notification.getWidth() >= font.getStringWidth(notification.getDescription()) + 32){
                notification.setScissorWidth((float)Interpolator.EASE_OUT.interpolate(notification.getScissorWidth(), 0, (0.12*100)/ Minecraft.getDebugFPS()));
                if((int)notification.getScissorWidth() == 0){
                    NotificationManager.removeNotification(notification);
                }
            }
        }
    }
}
