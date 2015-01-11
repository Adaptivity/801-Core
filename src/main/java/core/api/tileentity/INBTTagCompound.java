package core.api.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author Master801
 */
public interface INBTTagCompound {

	void readFromNBT(NBTTagCompound nbt);

	void writeToNBT(NBTTagCompound nbt);

}
