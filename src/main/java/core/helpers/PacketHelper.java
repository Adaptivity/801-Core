package core.helpers;

import core.api.common.mod.IMod;
import core.api.network.packet.ICustomPacket;
import core.api.network.packet.IPacketEventHandler;
import core.api.network.packet.PacketTypes;
import core.api.tileentity.IIncomingPacket;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.tileentity.TileEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Master801 on 10/25/2014.
 * @author Master801
 */
public final class PacketHelper {

    private static final Map<IMod, Map<FMLEventChannel, IPacketEventHandler>> PACKET_EVENT_HANDLER_MAP = new HashMap<IMod, Map<FMLEventChannel, IPacketEventHandler>>();

    /**
     * This automatically registers the IPacketEventHandler to the FMLEventChannel.
     */
    public static void addPacketEventHandler(IMod mod, FMLEventChannel channel, String channelName) {
        if (mod == null || channel == null || channelName == null) {
            return;
        }
        if (PacketHelper.PACKET_EVENT_HANDLER_MAP.containsKey(mod)) {
            throw new CoreNullPointerException("The PacketHelper mapping already contains a key for the IMod! IMod: '%s'", mod.toString());
        }
        Map<FMLEventChannel, IPacketEventHandler> privateMapping = PacketHelper.PACKET_EVENT_HANDLER_MAP.get(mod);
        if (privateMapping == null) {
            privateMapping = new HashMap<FMLEventChannel, IPacketEventHandler>();
            PacketHelper.PACKET_EVENT_HANDLER_MAP.put(mod, privateMapping);
        }
        IPacketEventHandler packetEventHandler = new PacketEventHandler(channel, channelName);
        if (privateMapping.containsKey(channel)) {
            throw new CoreNullPointerException("The PacketHelper private-mapping already contains a key for the FMLEventChannel! FMLEventChannel: '%s'", channel.toString());
        }
        if (privateMapping.containsValue(packetEventHandler)) {
            throw new CoreNullPointerException("The PacketHelper private-mapping alreadys contains a value for the IPacketEventHandler! IPacketEventHandler: '%s'", packetEventHandler.toString());
        }
        channel.register(packetEventHandler);
        privateMapping.put(channel, packetEventHandler);
        PacketHelper.injectNewMappingIntoExistingMapping(mod, privateMapping);
    }

    public static IPacketEventHandler getPacketEventHandler(IMod mod, FMLEventChannel channel) {
        if (PacketHelper.PACKET_EVENT_HANDLER_MAP.containsKey(mod) && PacketHelper.PACKET_EVENT_HANDLER_MAP.get(mod) != null) {
            return PacketHelper.PACKET_EVENT_HANDLER_MAP.get(mod).get(channel);
        }
        return null;
    }

    private static void injectNewMappingIntoExistingMapping(IMod mod, Map<FMLEventChannel, IPacketEventHandler> newMapping) {
        PacketHelper.PACKET_EVENT_HANDLER_MAP.put(mod, newMapping);
    }

    /**
     * Created by Master801 on 10/25/2014.
     * <p>
     *     The events only fire when an instance of this gets registered to your own mod.
     * </p>
     * <p>
     *     Modeled after Forestry's packet event handler.
     * </p>
     * @author Master801
     */
    private static final class PacketEventHandler implements IPacketEventHandler {

        private final FMLEventChannel channel;
        private final String channelName;

        public PacketEventHandler(FMLEventChannel channel, String channelName) {
            this.channel = channel;
            this.channelName = channelName;
        }

        @Override
        public FMLEventChannel getChannel() {
            return channel;
        }

        @Override
        public String getChannelName() {
            return channelName;
        }

        @SubscribeEvent
        public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
            NetHandlerPlayClient clientHandler = (NetHandlerPlayClient)event.handler;
            if (event.packet.channel().equals(getChannelName())) {
                onPacket(event.side(), null, event.packet.channel(), event.packet.payload().readByte(), event.packet.payload());
            }
        }

        @SubscribeEvent
        public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
            NetHandlerPlayServer serverHandler = (NetHandlerPlayServer)event.handler;
            if (event.packet.channel().equals(getChannelName())) {
                onPacket(event.side(), ((NetHandlerPlayServer) event.handler).playerEntity, event.packet.channel(), event.packet.payload().readByte(), event.packet.payload());
            }
        }

        @Override
        public void onPacket(Side side, EntityPlayer player, String channelName, byte packetID, ByteBuf buffer) {
            if (CoreResources.getSide().isServer()) {
                if (packetID == PacketTypes.ENTITY.getPacketID()) {
                    Entity entity = player.getEntityWorld().getEntityByID(buffer.readInt());
                    if (entity instanceof IIncomingPacket) {
                        ((IIncomingPacket)entity).onIncomingPacket(channelName, packetID, buffer);
                    }
                    return;
                }
                int xCoord = buffer.readInt();//DO NOT REMOVE! These are used to get the buffer's integer.
                int yCoord = buffer.readInt();//DO NOT REMOVE! These are used to get the buffer's integer.
                int zCoord = buffer.readInt();//DO NOT REMOVE! These are used to get the buffer's integer.
                TileEntity tile = player.getEntityWorld().getTileEntity(xCoord, yCoord, zCoord);
                if (tile instanceof IIncomingPacket) {
                    ((IIncomingPacket) tile).onIncomingPacket(channelName, packetID, buffer);
                }
            }
        }

        @Override
        public void sendPacket(ICustomPacket packet) {
            if (CoreResources.getSide().isClient()) {
//                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Packet handling has been disabled due to null payloads (ByteBuf)...");//FIXME
//                return;
                getChannel().sendToServer(packet.getFMLPacket());
            } else {
//                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Packet handling has been disabled due to null payloads (ByteBuf)...");//FIXME
//                return;
                getChannel().sendToAll(packet.getFMLPacket());
            }
        }

        @Override
        public void sendPacketToPlayer(ICustomPacket packet, EntityPlayerMP player) {
//            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Packet handling has been disabled due to null payloads (ByteBuf)...");//FIXME
//            return;
            getChannel().sendTo(packet.getFMLPacket(), player);
        }

    }

}
