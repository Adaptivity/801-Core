package core.api.network.packet;

import core.utilities.Coordinates;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import net.minecraft.util.ChunkCoordinates;

/**
 * Created by Master801 on 10/22/2014.
 * @author Master801
 */
public interface ICustomPacket {

    byte getPacketID();

    ByteBuf getRawData();

    ICustomPacket setRawData(ByteBuf buffer);

    /**
     * You should inject your tile's coords into the buffer here.
     * <p>
     *     Then you should set your custom data in another method.
     * </p>
     */
    void writeData(ByteBuf buffer);

    /**
     * You should read and set your tile's coords here.
     * <p>
     *     Then you should read and set custom data in another method.
     * </p>
     */
    void readData(ByteBuf buffer);

    String getChannelName();

    FMLProxyPacket getFMLPacket();

    Object getValue();

    ICustomPacket setValue(Object newValue);

    Coordinates getTileEntityCoords();

}
