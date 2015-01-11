package core.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * @author Master801
 */
public final class ItemBlockHelper {

	public static ItemBlock getItemBlockFromBlock(Block block) {
		return (ItemBlock) Item.getItemFromBlock(block);
	}

}
