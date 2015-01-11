package core.api.network.packet;

/**
 * @author Master801
 */
public enum PacketTypes {

    INTEGER((byte)-999999999),//This is a lot of fucking data to be sent.

    DOUBLE((byte)-999999998),

    BYTE((byte)-999999997),

    ITEM_STACK((byte)-999999996),

    ENTITY((byte)-999999995);

    private final byte packetID;

    private PacketTypes(byte packetID) {
        this.packetID = packetID;
    }

    public byte getPacketID() {
        return packetID;
    }

}
