package core.api.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Master801 on 11/13/2014.
 * @author Master801
 */
public interface INeighborChangedHook {

    /**
     * Called when a tile entity on a side of this block changes is created or is destroyed.
     */
    void onNeighborTileChange(TileEntity neighborTile);

    void onNeighborBlockChange(Block neighborBlock);

}
