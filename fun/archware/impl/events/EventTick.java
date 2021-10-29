package fun.archware.impl.events;

import fun.archware.base.event.Event;

public class EventTick extends Event {
    private float partialTicks;

    public EventTick(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
