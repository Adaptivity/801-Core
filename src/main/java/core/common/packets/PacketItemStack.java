package core.common.packets;

import core.api.network.packet.PacketTypes;
import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

/**
 * Created by Master801 on 11/19/2014.
 * @author Master801
 */
public class PacketItemStack extends PacketCoreBase<ItemStack> {

    public PacketItemStack(String channel, int xCoord, int yCoord, int zCoord) {
        super(channel, xCoord, yCoord, zCoord, PacketTypes.ITEM_STACK.getPacketID());
    }

    public PacketItemStack(String channel, TileEntity tile) {
        this(channel, tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public PacketItemStack(String channel, ChunkCoordinates coords) {
        this(channel, coords.posX, coords.posY, coords.posZ);
    }

    @Override
    protected void writeCustomData(ByteBuf buffer) {
        ByteBufUtils.writeItemStack(buffer, getValue());
    }

    @Override
    protected void readCustomData(ByteBuf buffer) {
        setValue(ByteBufUtils.readItemStack(buffer));
    }

}
