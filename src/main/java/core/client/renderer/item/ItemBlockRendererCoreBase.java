package core.client.renderer.item;

import core.client.renderer.block.BlockRendererCoreBase;
import core.utilities.Coordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

/**
 * This renders the block only in the inventory.
 * This should only be used for blocks that use a tile-entity special renderer.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class ItemBlockRendererCoreBase extends BlockRendererCoreBase {

	protected abstract String getItemRendererName();

    @Override
    protected abstract void renderInInventory(Block block, int metadata, RenderBlocks renderer, Tessellator tessellator);

    @Override
    protected final boolean renderBlock(IBlockAccess world, Coordinates coords, int modelID, RenderBlocks renderer, Tessellator tessellator) {
        return true;
    }

	@Override
	public String getRendererName() {
		return "801-Core.ItemBlockRenderer|" + getItemRendererName();
	}

    @Override
    protected boolean doesHaveCustomInventoryRenderer() {
        return true;
    }

}
