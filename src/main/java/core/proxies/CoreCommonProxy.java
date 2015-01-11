package core.proxies;

import core.Core;
import core.api.common.mod.IMod;
import core.api.network.proxy.IProxy;
import cpw.mods.fml.relauncher.Side;

public class CoreCommonProxy implements IProxy {

	@Override
	public void registerTileEntityRenderers() {
	}

    @Override
	public void registerBlockRenderers() {
	}

	@Override
	public void registerItemRenderers() {
	}

	@Override
	public void addRenderID(String id, int renderID) {
	}

	@Override
	public int getRenderIDFromSpecialID(String id) {
		return -1;
	}

	@Override
	public Side getSide() {
		return Side.SERVER;
	}

	@Override
	public IMod getOwningMod() {
		return Core.instance;
	}

}
