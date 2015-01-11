package core.item.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import core.event.items.EventEntityHangableSpawn;

/**
 * @author Master801
 */
public abstract class ItemSpawnHangableEntity extends ItemSpawnEntityBase {

	protected abstract Entity spawnNewEntity(ItemStack stack, World world, int xCoord, int yCoord, int zCoord, int side);

	protected ItemSpawnHangableEntity(boolean doesHaveSubtypes) {
		super(doesHaveSubtypes);
	}

	@Override
	protected void spawnEntity(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, int side) {
		switch(side) {
		case 0:
			return;
		case 1:
			return;
		default:
			if (!player.canPlayerEdit(xCoord, yCoord, zCoord, side, stack)) {
				break;
			}
			Entity spawnEntity = spawnNewEntity(stack, world, xCoord, yCoord, zCoord, side);
			if (MinecraftForge.EVENT_BUS.post(new EventEntityHangableSpawn.Pre(spawnEntity))) {
				break;
			}
			world.spawnEntityInWorld(spawnEntity);
			MinecraftForge.EVENT_BUS.post(new EventEntityHangableSpawn.Post(spawnEntity));
			break;
		}
	}

}
