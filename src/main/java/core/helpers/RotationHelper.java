package core.helpers;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Master801
 */
public final class RotationHelper {

	public static int convertForgeDirectionToInt(ForgeDirection direction) {
		return direction.ordinal();
	}

    public static byte convertForgeDirectionToByte(ForgeDirection direction) {
        return new Integer(RotationHelper.convertForgeDirectionToInt(direction)).byteValue();
    }

	public static ForgeDirection convertIntToForgeDirection(int direction) {
		if (direction < 0 || direction > 5) {
			return ForgeDirection.UNKNOWN;
		}
        return ForgeDirection.VALID_DIRECTIONS[direction];
	}

    public static ForgeDirection convertByteToForgeDirection(byte direction) {
        if (direction < new Integer(0).byteValue() || direction > new Integer(5).byteValue()) {
            return ForgeDirection.UNKNOWN;
        }
        return ForgeDirection.VALID_DIRECTIONS[new Byte(direction).intValue()];
    }

    public static byte calculateBlockRotation(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player) {
        byte rotation = new Integer(BlockPistonBase.determineOrientation(world, xCoord, yCoord, zCoord, player)).byteValue();
        if (rotation > -1 && rotation < RotationHelper.convertForgeDirectionToInt(ForgeDirection.UNKNOWN)) {
            return rotation;
        }
        return -1;
    }

}
