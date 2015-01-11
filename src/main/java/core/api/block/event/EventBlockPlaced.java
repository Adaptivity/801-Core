package core.api.block.event;

import net.minecraft.block.Block;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * @author Master801
 */
public class EventBlockPlaced extends Event {

	public final Block block;

	private EventBlockPlaced(Block block) {
		this.block = block;
	}

	/**
	 * @author Master801
	 */
	@Cancelable
	public static final class Pre extends EventBlockPlaced {

		public Pre(Block block) {
			super(block);
		}

	}

	/**
	 * @author Master801
	 */
	public static final class Post extends EventBlockPlaced {

		public Post(Block block) {
			super(block);
		}

	}

}
