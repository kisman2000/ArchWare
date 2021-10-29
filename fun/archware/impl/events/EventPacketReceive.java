package fun.archware.impl.events;

import fun.archware.base.event.Event;
import net.minecraft.network.Packet;

/**
 * Created by 1 on 01.04.2021.
 */
public class EventPacketReceive extends Event {

    Packet packet;

    public EventPacketReceive(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
