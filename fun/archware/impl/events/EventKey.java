package fun.archware.impl.events;

import fun.archware.base.event.Event;

/**
 * Created by 1 on 01.04.2021.
 */
public class EventKey extends Event {

    int key;

    public EventKey(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
