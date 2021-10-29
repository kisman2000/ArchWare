package fun.archware.impl.events;

import fun.archware.base.event.Event;

/**
 * Created by 1 on 01.04.2021.
 */
public class EventPreUpdate extends Event{

    double x, y, minY ,z;
    float yaw, pitch;
    boolean onGround;

    public EventPreUpdate(double x, double y, double z, float yaw, float pitch, boolean onGround, double minY) {
        this.x = x;
        this.onGround = onGround;
        this.pitch = pitch;
        this.yaw = yaw;
        this.y = y;
        this.z = z;
        this.minY = minY;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
