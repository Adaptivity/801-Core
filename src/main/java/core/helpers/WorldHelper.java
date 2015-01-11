package core.helpers;

import net.minecraft.world.World;

public final class WorldHelper {

	public static boolean isClient(World world) {
		return world.isRemote;
	}

	public static boolean isServer(World world) {
		return !world.isRemote;
	}

}
