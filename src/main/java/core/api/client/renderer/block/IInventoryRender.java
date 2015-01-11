package core.api.client.renderer.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;

/**
 * Created by Master801 on 10/2/2014.
 * @author Master801
 */
public interface IInventoryRender {

    boolean isBlockOpaque(int metadata);

    void renderBlockInInventory(Block block, int metadata, int modelID, RenderBlocks renderer);

}
