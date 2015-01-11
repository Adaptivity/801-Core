package core.client.renderer.block;

import core.Core;
import core.api.client.renderer.block.IInventoryRender;
import core.api.common.mod.IMod;
import core.utilities.Coordinates;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by Master801 on 10/2/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class BlockRendererInventory extends BlockRendererCoreBase {

    @Override
    public String getSpecialRenderID() {
        return "inventoryRenderer";
    }

    @Override
    public IMod getOwningMod() {
        return Core.instance;
    }

    /**
     * Handles all the if the block is null and/or model id does not match type stuff.
     */
    @Override
    protected boolean renderBlock(IBlockAccess world, Coordinates coords, int modelID, RenderBlocks renderer, Tessellator tessellator) {
        Block block = world.getBlock(coords.getX(), coords.getY(), coords.getZ());
        if (block instanceof IInventoryRender) {
            return ((IInventoryRender)block).isBlockOpaque(world.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()));
        }
        return false;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        if (block instanceof IInventoryRender) {
            ((IInventoryRender)block).renderBlockInInventory(block, metadata, modelID, renderer);
        }
        GL11.glPopMatrix();
    }

    @Override
    public String getRendererName() {
        return "801-Core.Block_Renderer|Inventory";
    }

}
