package core.item.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import core.helpers.WorldHelper;
import core.item.ItemCoreBase;

/**
 * @author Master801
 */
public abstract class ItemSpawnEntityBase extends ItemCoreBase {

	/**
	 * Make sure to not decrease the item's stack-size. It already does this for you.
	 * <p>
	 * This also checks if the item is ran on the server's side.
	 * </p>
	 */
	protected abstract void spawnEntity(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, int side);

	protected ItemSpawnEntityBase(boolean doesHaveSubtypes) {
		super(doesHaveSubtypes);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, int side, float hitX, float hitY, float hitZ) {
		if (WorldHelper.isServer(world)) {
			spawnEntity(stack, player, world, xCoord, yCoord, zCoord, side);
			stack.stackSize--;
			return true;
		}
		return false;
	}

}