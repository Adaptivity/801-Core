package core.item.tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import core.api.item.IPlaceableTorch;
import core.api.item.tool.ITool;
import core.helpers.ItemHelper;
import core.item.ItemCoreBase;

/**
 * @author Master801
 */
public abstract class ItemToolCore extends ItemCoreBase implements IPlaceableTorch {

	private final ItemTool tool;

	protected ItemToolCore(boolean doesHaveSubtypes, float par2, ToolMaterial toolMaterial, Set<Block> blockArray) {
		super(doesHaveSubtypes);
		if (isActualTool()) {
			setMaxStackSize(1);
			tool = new ItemTool(par2, toolMaterial, blockArray) {
			};
			tool.setHasSubtypes(hasSubtypes);
		} else {
			tool = null;
		}
	}

	@Override
	public boolean isItemAllowedToPlaceTorches() {
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, int side, float hitX, float hitY, float hitZ) {
		Item item = stack.getItem();
		Block block = world.getBlock(xCoord, yCoord, zCoord);
		TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
		if (item instanceof ITool) {
			return ItemHelper.onIToolUse((ITool)item, stack, world, new ChunkCoordinates(xCoord, yCoord, zCoord), block, tile, player, side);
		}
		return false;
	}

	protected boolean isActualTool() {
		return true;
	}

	public final ItemTool getTool() {
		return tool;
	}

}
