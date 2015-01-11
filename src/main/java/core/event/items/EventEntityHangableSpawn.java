package core.event.items;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * @author Master801
 */
public class EventEntityHangableSpawn extends Event {

	/**
	 * Can be null.
	 */
	public final Entity hangable;

	protected EventEntityHangableSpawn(Entity hangable) {
		this.hangable = hangable;
	}

	@Cancelable
	public static final class Pre extends EventEntityHangableSpawn {

		public Pre(Entity hangable) {
			super(hangable);
		}

	}

	public static final class Post extends EventEntityHangableSpawn {

		public Post(Entity hangable) {
			super(hangable);
		}

	}

}
