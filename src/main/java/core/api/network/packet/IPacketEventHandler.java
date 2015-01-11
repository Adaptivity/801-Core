package core.api.network.packet;

import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * Created by Master801 on 10/25/2014.
 * <p>
 *     Somewhat based off of my "PacketEventHandler" Class in "Transformer Convertors 2".
 * <p/>
 * @author Master801
 */
public interface IPacketEventHandler {

    FMLEventChannel getChannel();

    String getChannelName();

    void onPacket(Side side, EntityPlayer player, String channelName, byte packetID, ByteBuf buffer);

    void sendPacket(ICustomPacket packet);

    void sendPacketToPlayer(ICustomPacket packet, EntityPlayerMP player);

}
