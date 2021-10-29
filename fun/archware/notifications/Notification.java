package fun.archware.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Notification {
    private String description;
    private String title;
    private final NotificationType notificationType;
    private double posY, endPosY;
    private double posX;
    private double lastTickPosY;
    private double lastTickPosX;
    private double width, scissorWidth;
    private boolean passed;
    private final long startTime;

    public Notification(final String title, final String description, NotificationType notificationType) {
        this.title = title;
        this.description = description;
        this.notificationType = notificationType;
        startTime = System.currentTimeMillis();
        posY = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 22;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public double getPosY() {
        return posY;
    }

    public void setPosY(final double posY) {
        this.posY = posY;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isPassed() {
        return passed;
    }

    public double getLastTickPosY() {
        return lastTickPosY;
    }

    public void setLastTickPosY(final double lastTickPosY) {
        this.lastTickPosY = lastTickPosY;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getLastTickPosX() {
        return lastTickPosX;
    }

    public void setLastTickPosX(double lastTickPosX) {
        this.lastTickPosX = lastTickPosX;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public long getStartTime() {
        return startTime;
    }

    public double getScissorWidth(){
        return scissorWidth;
    }

    public void setScissorWidth(double scissorWidth) {
        this.scissorWidth = scissorWidth;
    }

    public String getTitle() {
        return title;
    }
}
