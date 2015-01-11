package core.common.packets;

import core.Core;
import core.api.network.packet.ICustomPacket;
import core.api.network.packet.PacketTypes;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import core.utilities.Coordinates;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.tileentity.TileEntity;

/**
 * Base-Class for making Packets easier.
 * <p>
 * Created by Master801 on 10/24/2014.
 * <p/>
 * @author Master801
 */
public abstract class PacketCoreBase<T> implements ICustomPacket {

    protected abstract void writeCustomData(ByteBuf buffer);

    protected abstract void readCustomData(ByteBuf buffer);

    private final String channel;
    private T value = null;
    private Coordinates coords;
    private ByteBuf buffer = null;
    private byte packetID;
    public boolean isCustomPacket = false;

    protected PacketCoreBase(String channel, Coordinates coords, byte packetID) {
        this(channel, coords.getX(), coords.getY(), coords.getZ(), packetID);
    }

    protected PacketCoreBase(String channel, TileEntity tile, byte packetID) {
        this(channel, tile.xCoord, tile.yCoord, tile.zCoord, packetID);
    }

    protected PacketCoreBase(String channel, int xCoord, int yCoord, int zCoord, byte packetID) {
        this.channel = channel;
        this.packetID = packetID;
        if (buffer == null) {
            buffer = Unpooled.buffer();
        }
        if (packetID == PacketTypes.ENTITY.getPacketID()) {
            return;
        }
        coords = new Coordinates(xCoord, yCoord, zCoord);
    }

    @Override
    public final byte getPacketID() {
        return packetID;
    }

    @Override
    public final ByteBuf getRawData() {
        return buffer;
    }

    @Override
    public final ICustomPacket setRawData(ByteBuf buffer) {
        this.buffer = buffer;
        return this;
    }

    @Override
    public FMLProxyPacket getFMLPacket() {
        if (getRawData() == null || getChannelName() == null) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The packet's raw data and/or channel name is null.");
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Is Raw Data null: '%s', Channel Name: '%s'", String.valueOf(getRawData() == null), getChannelName());
            return null;
        }
        writeData(getRawData());
        FMLProxyPacket packet = new FMLProxyPacket(getRawData(), getChannelName());
        packet.setTarget(getTargetPacketSide());
        return packet;
    }

    protected Side getTargetPacketSide() {
        return Side.SERVER;
    }

    @Override
    public final String getChannelName() {
        return channel;
    }

    @Override
    public final ICustomPacket setValue(Object newValue) {
        value = (T)newValue;
        return this;
    }

    public final T getValue() {
        return value;
    }

    /**
     * You should inject your tile's coords into the buffer here.
     * <p>
     *     Then you should set your custom data in another method.
     * </p>
     */
    @Override
    public final void writeData(ByteBuf buffer) {
        if (isCustomPacket) {
            return;
        }
        buffer.writeByte(getPacketID());
        if (coords != null) {
            buffer.writeInt(coords.getX());
            buffer.writeInt(coords.getY());
            buffer.writeInt(coords.getZ());
        }
        writeCustomData(buffer);
    }

    /**
     * You should read and set your tile's coords here.
     * <p>
     *     Then you should read and set custom data in another method.
     * </p>
     */
    @Override
    public final void readData(ByteBuf buffer) {
        if (isCustomPacket) {
            return;
        }
        byte packetID = buffer.readByte();//The packet's id. This is unused when reading.
        if (coords != null) {
            coords.setCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt());
        }
        readCustomData(buffer);
    }

    @Override
    public Coordinates getTileEntityCoords() {
        return coords;
    }

}
