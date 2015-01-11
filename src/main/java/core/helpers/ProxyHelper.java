package core.helpers;

import core.Core;
import core.api.common.mod.IMod;
import core.api.network.proxy.IProxy;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Master801
 */
public final class ProxyHelper {

    private static final Map<IMod, Map<Side, IProxy>> PROXY_MAPPING = new HashMap<IMod, Map<Side, IProxy>>();
    private static final Map<IProxy, Map<String, Integer>> PROXY_RENDER_ID_MAPPING = new HashMap<IProxy, Map<String, Integer>>();

    private ProxyHelper() {
    }

    public static void addProxyToMapping(IProxy proxy) {
        if (proxy == null) {
            throw new CoreNullPointerException("The Proxy is null!");
        }
        if (proxy.getSide() == null) {
            throw new CoreNullPointerException("The Proxy's side is undefined!");
        }
        if (proxy.getOwningMod() == null) {
            throw new CoreNullPointerException("The Proxy's owning mod is null!");
        }
        if (!ModHelper.isModRegistered(proxy.getOwningMod().getClass()).booleanValue()) {
            throw new CoreNullPointerException("The Proxy's owning mod is not registered!");
        }
        if (!PROXY_MAPPING.containsKey(proxy.getOwningMod())) {
            HashMap<Side, IProxy> proxyHashMap = new HashMap<Side, IProxy>();
            proxyHashMap.put(proxy.getSide(), proxy);
            if (!PROXY_MAPPING.containsValue(proxyHashMap)) {
                PROXY_MAPPING.put(proxy.getOwningMod(), proxyHashMap);
            } else {
                LoggerHelper.addAdvancedMessageToLogger(proxy.getOwningMod(), LoggerEnum.WARN, "The Proxy mapping already contains that proxy! Mod: " + proxy.getOwningMod() + " Proxy: " + proxy);
            }
        } else {
            LoggerHelper.addMessageToLogger(proxy.getOwningMod(), LoggerEnum.WARN, "There's already a registered mod on that proxy mapping! Mod: " + proxy.getOwningMod() + " Proxy: " + proxy);
        }
    }

    public static IProxy getSidedProxyFromMod(IMod mod, Side side) {
        if (mod == null) {
            throw new CoreNullPointerException("The Mod is null!");
        }
        Map<Side, IProxy> hiddenProxyMapping = PROXY_MAPPING.get(mod);
        if (hiddenProxyMapping == null) {
            PROXY_MAPPING.put(mod, new HashMap<Side, IProxy>());
            return null;
        }
        IProxy proxy = hiddenProxyMapping.get(side);
        if (proxy == null) {
            throw new CoreNullPointerException("The Proxy I tried to get is null! Mod: '%s' Side: '%s'", mod.getModID(), side.toString());
        }
        return proxy;
    }

    public static int getRenderIDFromSpecialID(IProxy proxy, String id) {
        if (proxy == null) {
            throw new CoreNullPointerException("The IProxy is null!");
        }
        if (id == null) {
            throw new CoreNullPointerException("The Special ID is null!");
        }
        int renderID = -1;
        if (proxy.getSide() == Side.SERVER) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The IProxy provided was attempting to load on the Server side, and thought you should know this! IProxy: '%s'", (String)proxy.toString());
            return renderID;//This is merely a joke.
        }
        Map<String, Integer> renderIDMapping = PROXY_RENDER_ID_MAPPING.get(proxy);
        if (renderIDMapping == null) {
            renderIDMapping = new HashMap<String, Integer>();
        }
        if (!renderIDMapping.containsKey(id)) {
            renderID = RenderingRegistry.getNextAvailableRenderId();
            renderIDMapping.put(id, renderID);
            PROXY_RENDER_ID_MAPPING.put(proxy, renderIDMapping);
        }
        return renderIDMapping.get(id);
    }

    public static void addRenderIDToMapping(IProxy proxy, String id, int renderID) {
        if (proxy.getSide() == Side.SERVER) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The IProxy that was provided was attempting to load on the Server side, and thought you should know this! IProxy: '%s'", proxy.toString());
        }
        Map<String, Integer> map = PROXY_RENDER_ID_MAPPING.get(proxy);
        if (!map.containsValue(renderID)) {
            map.put(id, renderID);
            PROXY_RENDER_ID_MAPPING.put(proxy, map);
        }
    }

    public static boolean isProxyRegisted(IProxy proxy) {
        Map<Side, IProxy> map = ProxyHelper.PROXY_MAPPING.get(proxy.getOwningMod());
        if (map != null) {
            if (!map.containsKey(proxy.getSide()) || !map.containsValue(proxy)) {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "The IProxy is not registered. Mod: '%s'", proxy.getOwningMod().getModID());
                return false;
            } else {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "The IProxy is registered! Mod: '%s'", proxy.getOwningMod().getModID());
                return true;
            }
        }
        return false;
    }

}
