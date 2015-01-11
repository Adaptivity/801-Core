package core.common.packets;

import core.api.network.packet.PacketTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * Created by Master801 on 10/22/2014.
 * @author Master801
 */
public class PacketInteger extends PacketCoreBase<Integer> {

    public PacketInteger(String channel, int xCoord, int yCoord, int zCoord) {
        super(channel, xCoord, yCoord, zCoord, PacketTypes.INTEGER.getPacketID());
    }

    public PacketInteger(String channel, TileEntity tile) {
        this(channel, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public PacketInteger(String channel, ChunkCoordinates coords) {
        this(channel, coords.posX, coords.posY, coords.posZ);
    }

    /**
     * You should inject your own data into the buffer here.
     */
    @Override
    public void writeCustomData(ByteBuf buffer) {
        buffer.writeInt(getValue());
    }

    /**
     * You should read and set your custom data values here.
     */
    @Override
    public void readCustomData(ByteBuf buffer) {
        setValue(buffer.readInt());
    }

}
