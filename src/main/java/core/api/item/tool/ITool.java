package core.api.item.tool;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

/**
 * @author Master801
 */
public interface ITool {

	boolean isBlockAllowedToRotate(Block block, int metadata);

	boolean onItemUse(ITool tool, ItemStack stack, World world, ChunkCoordinates coords, Block block, TileEntity tile, EntityPlayer player, int side);

}
