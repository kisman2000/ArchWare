package fun.archware.impl.utils;

public class Particle {
    private float posX;
    private float posY;
    private int opacity;
    private float rotation;

    public Particle(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setOpacity(final int opacity){
        this.opacity = opacity;
    }

    public int getOpacity() {
        return opacity;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}
