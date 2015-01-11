package core.common.packets;

import core.api.network.packet.PacketTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * Created by Master801 on 11/19/2014.
 * @author Master801
 */
public class PacketDouble extends PacketCoreBase<Double> {

    public PacketDouble(String channel, int xCoord, int yCoord, int zCoord) {
        super(channel, xCoord, yCoord, zCoord, PacketTypes.DOUBLE.getPacketID());
    }

    public PacketDouble(String channel, TileEntity tile) {
        this(channel, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public PacketDouble(String channel, ChunkCoordinates coords) {
        this(channel, coords.posX, coords.posY, coords.posZ);
    }

    @Override
    protected void writeCustomData(ByteBuf buffer) {
        buffer.writeDouble(getValue());
    }

    @Override
    protected void readCustomData(ByteBuf buffer) {
        setValue(buffer.readDouble());
    }

}
