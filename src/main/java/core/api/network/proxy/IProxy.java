package core.api.network.proxy;

import core.api.common.mod.IMod;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Master801
 */
public interface IProxy {

	void registerBlockRenderers();

	void registerTileEntityRenderers();

	void registerItemRenderers();

	void addRenderID(String id, int renderID);

	int getRenderIDFromSpecialID(String id);

	Side getSide();

	IMod getOwningMod();

}
