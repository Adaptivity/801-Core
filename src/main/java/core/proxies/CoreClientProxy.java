package core.proxies;

import core.Core;
import core.api.common.mod.IMod;
import core.api.network.proxy.IProxy;
import core.client.renderer.block.BlockRendererInventory;
import core.helpers.ProxyHelper;
import core.helpers.RegistryHelper;
import cpw.mods.fml.relauncher.Side;

public class CoreClientProxy implements IProxy {

    @Override
	public void registerBlockRenderers() {
        RegistryHelper.registerBlockHandler(new BlockRendererInventory());
	}

	@Override
	public void registerTileEntityRenderers() {
	}

	@Override
	public void registerItemRenderers() {
	}

	@Override
	public void addRenderID(String id, int renderID) {
		ProxyHelper.addRenderIDToMapping(ProxyHelper.getSidedProxyFromMod(getOwningMod(), Side.CLIENT), id, renderID);
	}

	@Override
	public int getRenderIDFromSpecialID(String id) {
		return ProxyHelper.getRenderIDFromSpecialID(ProxyHelper.getSidedProxyFromMod(getOwningMod(), Side.CLIENT), id);
	}

	@Override
	public Side getSide() {
		return Side.CLIENT;
	}

	@Override
	public IMod getOwningMod() {
		return Core.instance;
	}

}
