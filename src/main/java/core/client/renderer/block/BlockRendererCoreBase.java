package core.client.renderer.block;

import core.api.common.mod.IMod;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.helpers.BlockRendererHelper;
import core.helpers.ProxyHelper;
import core.utilities.Coordinates;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Based-heavily off of my BluePower 'RendererBlockBluePowerBase'.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class BlockRendererCoreBase implements ISimpleBlockRenderingHandler {

    public abstract String getSpecialRenderID();

    public abstract IMod getOwningMod();

    /**
    * Handles all the if the block is null and/or model id does not match type stuff.
     * <p>
     *     ChunkCoordinates is removed in 1.8.
     * </p>
    */
    @Deprecated
    public boolean renderBlock(IBlockAccess world, ChunkCoordinates coords, int metadata, Block block, int modelID, RenderBlocks renderer, Tessellator tessellator) {
        return false;
    }

    protected abstract boolean renderBlock(IBlockAccess world, Coordinates coords, int modelID, RenderBlocks renderer, Tessellator tessellator);

    public abstract String getRendererName();

    protected BlockRendererCoreBase() {
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        if (block == null) {
            throw new CoreNullPointerException("The Block is null!");
        }
        Tessellator tessellator = new Tessellator();
        if (doesHaveCustomInventoryRenderer()) {
            GL11.glPushMatrix();
            block.setBlockBoundsForItemRender();
            renderer.lockBlockBounds = false;
            renderer.setRenderBoundsFromBlock(block);
            renderInInventory(block, metadata, renderer, tessellator);
            GL11.glPopMatrix();
            return;
        }
        GL11.glPushMatrix();
        block.setBlockBoundsForItemRender();
        renderer.lockBlockBounds = false;
        renderer.setRenderBoundsFromBlock(block);
        BlockRendererHelper.renderBlockAsItem(renderer, Tessellator.instance, block, metadata);
        GL11.glPopMatrix();
	}

    @Override
    public final boolean renderWorldBlock(IBlockAccess world, int xCoord, int yCoord, int zCoord, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = new Tessellator();
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, xCoord, yCoord, zCoord));
        if (renderBlock(world, new ChunkCoordinates(xCoord, yCoord, zCoord), world.getBlockMetadata(xCoord, yCoord, zCoord), block, modelId, renderer, tessellator)) {
            return true;
        }
        if (renderBlock(world, new Coordinates(xCoord, yCoord, zCoord), modelId, renderer, tessellator)) {
            return true;
        }
        return renderer.renderStandardBlock(block, xCoord, yCoord, zCoord);
    }

    @Override
    public boolean shouldRender3DInInventory(int modelID) {
        return true;
    }

    @Override
    public final int getRenderId() {
        return getRenderID();
    }

    protected int getRenderID() {
        return ProxyHelper.getRenderIDFromSpecialID(ProxyHelper.getSidedProxyFromMod(getOwningMod(), Side.CLIENT), getSpecialRenderID());
    }

    protected boolean doesHaveCustomInventoryRenderer() {
        return false;
    }

    /**
     * Gets called from the "doesHaveCustomInventoryRender" method.
     * Make sure to override that method, and return true.
     */
    protected void renderInInventory(Block block, int metadata, RenderBlocks renderer, Tessellator tessellator) {
    }

}
