package core.common.packets;

import core.api.network.packet.PacketTypes;
import core.utilities.Coordinates;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;

/**
 * Created by Master801 on 12/9/2014 at 6:06 PM.
 * @author Master801
 */
public abstract class PacketEntityCoreBase extends PacketCoreBase<Integer> {

    protected abstract void writeCustomEntityData(ByteBuf buffer);

    protected abstract void readCustomEntityData(ByteBuf buffer);

    protected final World world;

    protected PacketEntityCoreBase(String channel, World world, int entityID) {
        super(channel, new Coordinates(-1, -1, -1), PacketTypes.ENTITY.getPacketID());
        setValue(entityID);
        this.world = world;
    }

    @Override
    protected final void writeCustomData(ByteBuf buffer) {
        writeCustomEntityData(buffer);
        buffer.writeInt(getValue().intValue());
    }

    @Override
    protected final void readCustomData(ByteBuf buffer) {
        readCustomEntityData(buffer);
        setValue(buffer.readInt());
    }

}
