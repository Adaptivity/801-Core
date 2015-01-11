package core.api.tileentity;

import io.netty.buffer.ByteBuf;

/**
 * Created by Master801 on 10/28/2014.
 * <p>
 *     Make sure to handle the incoming packet.
 * </p>
 * @author Master801
 */
public interface IIncomingPacket {

    /**
     * Make sure you <b> DO NOT </b> read the packet's coordinates or id.
     * This has already been done for you.
     */
    void onIncomingPacket(String channel, byte packetID, ByteBuf buffer);

}
