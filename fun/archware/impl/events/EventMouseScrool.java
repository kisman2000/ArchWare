package fun.archware.impl.events;

import fun.archware.base.event.Event;

public class EventMouseScrool extends Event {
    private float velocity;

    public EventMouseScrool(float velocity) {
        this.velocity = velocity;
    }

    public float getVelocity() {
        return velocity;
    }
}
