/*
 * noom (c) 2021.
 */

/*
 * noom (c) 2021.
 */

package fun.archware.impl.events;

import fun.archware.base.event.Event;

/**
 * Created by 1 on 02.04.2021.
 */
public class EventSlowdown extends Event {

    public float moveStrafing, moveForvard;

    public EventSlowdown(float moveStrafing, float moveForvard) {
        this.moveStrafing = moveStrafing;
        this.moveForvard = moveForvard;
    }

    public float getMoveStrafing() {
        return moveStrafing;
    }

    public void setMoveStrafing(float moveStrafing) {
        this.moveStrafing = moveStrafing;
    }

    public float getMoveForvard() {
        return moveForvard;
    }

    public void setMoveForvard(float moveForvard) {
        this.moveForvard = moveForvard;
    }
}
