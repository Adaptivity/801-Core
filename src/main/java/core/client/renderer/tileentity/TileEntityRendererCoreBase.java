package core.client.renderer.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public abstract class TileEntityRendererCoreBase extends TileEntitySpecialRenderer {

	protected abstract void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f);

	@Override
	public final void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		renderTileEntity(tileentity, d0, d1, d2, f);
	}

}
