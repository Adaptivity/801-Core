package core.api.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Only used by the ItemBlock.
 * @author Master801
 */
public interface IPlaceable {

	/**
	 * This is called by the ItemBlock.
	 */
	boolean onBlockPlacedBy(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, ForgeDirection side);

}
