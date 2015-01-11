package core.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import core.Core;
import core.api.common.mod.IMod;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;

/**
 * @author Master801
 */
public final class ChunkLoaderHelper {

	private static final Map<IMod, Ticket> ticketsMap = new HashMap<IMod, Ticket>();
	private static final List<Ticket> ticketsList = new ArrayList<Ticket>();

	private ChunkLoaderHelper() {
	}

	/**
	 * Easy registering of a Chunk Ticket.
	 * @author Master801
	 */
	public static Ticket registerChunkTicket(IMod mod, World world, Type type) {
		final Ticket ticket = ForgeChunkManager.requestTicket(mod, world, type);
		if (ticket == null) {
			throw new CoreNullPointerException("The Ticket you tried to get is null, or exceeding chunk loading ranges.");
		}
		if (ticketsMap.containsKey(mod) && ticketsMap.containsValue(ticket)) {
			return ticketsMap.get(mod);
		} else {
			ticketsMap.put(mod, ticket);
			if (ticketsList.contains(ticket)) {
				throw new CoreNullPointerException("The Ticket already exists!");
			} else {
				ticketsList.add(ticket);
				return ticket;
			}
		}
	}

	public static Ticket getRegisteredChunkTicket(IMod mod, World world) {
		if (mod == null) {
			throw new CoreNullPointerException("The Mod is null!");
		}
		if (world == null) {
			throw new CoreNullPointerException("The World is null!");
		}
		if (!ticketsMap.containsKey(mod)) {
			throw new CoreNullPointerException("The Tickets Map does not contain an entry for the mod " + mod.getModID() + "!");
		}
		return ticketsMap.get(mod);
	}

	public static void keepChunkLoaded(Ticket ticket, IMod mod, World world, int xCoord, int zCoord) {
		if (WorldHelper.isServer(world)) {
			ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(xCoord / 16, zCoord / 16));
		} else {
			LoggerHelper.addMessageToLogger(mod, LoggerEnum.WARN, "The World is not on the server-side.");
		}
	}

	public static void unloadChunk(Ticket ticket, IMod mod, World world, int xCoord, int zCoord) {
		if (WorldHelper.isServer(world)) {
			ForgeChunkManager.unforceChunk(ticket, new ChunkCoordIntPair(xCoord / 16, zCoord / 16));
		} else {
			LoggerHelper.addMessageToLogger(mod, LoggerEnum.WARN, "The World is not on the server-side.");
		}
	}

	public static void deleteChunkLoader(Ticket ticket) {
		if (ticket == null) {
			throw new CoreNullPointerException("The Ticket is null!");
		} else {
			ForgeChunkManager.releaseTicket(ticket);
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.INFO, "Successfully released Chunk Loading Ticket!");
		}
	}

	public static boolean isTicketAlreadyRegistered(Ticket ticket) {
		if (ticket == null) {
			throw new CoreNullPointerException("The Ticket is null1");
		} else {
			return ticketsList.contains(ticket);
		}
	}

}
