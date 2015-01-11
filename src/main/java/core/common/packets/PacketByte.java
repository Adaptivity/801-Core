package core.common.packets;

import core.api.network.packet.PacketTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * Created by Master801 on 11/19/2014.
 * @author Master801
 */
public class PacketByte extends PacketCoreBase<Byte> {

    public PacketByte(String channel, int xCoord, int yCoord, int zCoord) {
        super(channel, xCoord, yCoord, zCoord, PacketTypes.BYTE.getPacketID());
    }

    public PacketByte(String channel, TileEntity tile) {
        this(channel, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public PacketByte(String channel, ChunkCoordinates coords) {
        this(channel, coords.posX, coords.posY, coords.posZ);
    }

    @Override
    protected void writeCustomData(ByteBuf buffer) {
        buffer.writeByte(getValue());
    }

    @Override
    protected void readCustomData(ByteBuf buffer) {
        setValue(buffer.readByte());
    }


}
