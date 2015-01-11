package core.helpers;

import java.util.HashMap;
import java.util.Map;

import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

/**
 * @author Master801
 */
public final class NetworkHelper {

	private static final Map<String, SimpleNetworkWrapper> NETWORK_MAP = new HashMap<String, SimpleNetworkWrapper>();
	private static final Map<String, FMLEventChannel> NETWORK_EVENT_MAP = new HashMap<String, FMLEventChannel>();

	/**
	 * Makes a new SimpleNetworkWrapper.
	 */
	public static SimpleNetworkWrapper makeNewSimpleNetworkWrapper(String networkChannelName) {
        if (networkChannelName == null) {
            throw new CoreNullPointerException("The Network Channel Name is null!");
        }
        SimpleNetworkWrapper wrapper = NETWORK_MAP.get(networkChannelName);
        if (wrapper == null) {
            wrapper = CoreResources.getNetworkRegistry().newSimpleChannel(networkChannelName);
        }
		if (wrapper == null) {
			throw new CoreNullPointerException("Could not make a new Channel Handler!");
		}
        if (!NETWORK_MAP.containsKey(networkChannelName) && !NETWORK_MAP.containsValue(wrapper)) {
            NETWORK_MAP.put(networkChannelName, wrapper);
        }
		return wrapper;
	}

	/**
	 * Make sure you register that event-handler Class as the event-handler for the channel!
	 */
	public static FMLEventChannel getEventChannel(String channelName) {
		if (channelName == null) {
			throw new CoreNullPointerException("Channel name is null!");
		}
		FMLEventChannel channel = NETWORK_EVENT_MAP.get(channelName);
        if (channel == null) {
			channel = CoreResources.getNetworkRegistry().newEventDrivenChannel(channelName);
		}
		if (channel == null) {
			throw new CoreNullPointerException("EventChannel is null!");
		}
        if (!NETWORK_EVENT_MAP.containsKey(channelName) && !NETWORK_EVENT_MAP.containsValue(channel)) {
            NETWORK_EVENT_MAP.put(channelName, channel);
        }
		return channel;
	}

}