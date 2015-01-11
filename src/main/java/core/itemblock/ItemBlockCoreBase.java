package core.itemblock;

import core.api.block.IPlaceable;
import core.api.block.event.EventBlockPlaced;
import core.helpers.RotationHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

public abstract class ItemBlockCoreBase extends ItemBlock {

	protected abstract String getUnlocalizedName(int metadata);

    @SideOnly(Side.CLIENT)
	protected abstract List<String> addInformation(ItemStack stack, EntityPlayer player, boolean var3);

	public final Block block = field_150939_a;

	public ItemBlockCoreBase(Block block, boolean hasMetadata) {
		super(block);
		if (hasMetadata) {
			setMaxDamage(0);
			setHasSubtypes(true);
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack stack) {
        if (overrideTileInUnlocalizedName()) {
            return getUnlocalizedName(stack.getItemDamage());
        }
		return "tile." + getUnlocalizedName(stack.getItemDamage());
	}

    protected boolean overrideTileInUnlocalizedName() {
        return false;
    }

	@Override
    @SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean var4) {
		list = addInformation(stack, player, var4);
		super.addInformation(stack, player, list, var4);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, int side, float hitX, float hitY, float hitZ, int metadata) {
		if(MinecraftForge.EVENT_BUS.post(new EventBlockPlaced.Pre(block))) {
			return false;
		}
		if (!world.setBlock(xCoord, yCoord, zCoord, block, metadata, 3)) {
			return false;
		}
		if (block == world.getBlock(xCoord, yCoord, zCoord)) {
			block.onBlockPlacedBy(world, xCoord, yCoord, zCoord, player, stack);
			block.onPostBlockPlaced(world, xCoord, yCoord, zCoord, metadata);
		}
		if (block instanceof IPlaceable) {
			return ((IPlaceable)block).onBlockPlacedBy(stack, player, world, xCoord, yCoord, zCoord, RotationHelper.convertIntToForgeDirection(side));
		}
		MinecraftForge.EVENT_BUS.post(new EventBlockPlaced.Post(block));
		return true;
	}

}
