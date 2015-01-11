package core.api.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Only used in tile-entities, for telling the Block that it can rotate.
 * @author Master801
 */
public interface IRotatable {

	/**
	 * If the Block may (as in, you want the block to rotate), rotate, this should return true.
	 */
	boolean canBlockRotate();

	byte getRotation();

    /**
     * This just converts the rotation to a ForgeDirection.
     */
    ForgeDirection getDirection();

	void setRotation(byte rotation);

    void addRotation(byte rotation);

	/**
	 * This rotates the block in some kind of direction.
	 */
	void rotateBlock();

}
