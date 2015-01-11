package core.event.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import core.api.item.tool.ITool;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * Called when the ITool is 'hitting' (rotating) a block.
 * @author Master801
 */
public class EventRotation extends Event {

	/**
	 * The item you're using to rotate the block.
	 */
	public final ItemStack rotationItem;

	/**
	 * The block you're hitting to rotate.
	 */
	public final Block block;

	/**
	 * The tool.
	 */
	public final ITool tool;

    private EventRotation() {
        rotationItem = null;
        block = null;
        tool = null;
    }

	protected EventRotation(ItemStack rotationItem, Block block, ITool tool) {
		this.rotationItem = rotationItem;
		this.block = block;
		this.tool = tool;
	}

    /**
     * @author Master801
     */
	@Cancelable
	public static final class Pre extends EventRotation {

		public Pre(ItemStack rotationItem, Block block, ITool tool) {
			super(rotationItem, block, tool);
		}

	}

    /**
     * @author Master801
     */
	public static final class Post extends EventRotation {

		/**
		 * Only used for adding more rotating block things.
		 */
		public Post(ItemStack rotationItem, Block block, ITool tool) {
			super(rotationItem, block, tool);
		}

	}

}
