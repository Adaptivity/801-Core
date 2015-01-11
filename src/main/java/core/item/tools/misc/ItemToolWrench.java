package core.item.tools.misc;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import core.api.item.tool.ITool;
import core.helpers.ItemHelper;
import core.item.tools.ItemToolCore;

/**
 * @author Master801
 */
public class ItemToolWrench extends ItemToolCore implements ITool {

	public ItemToolWrench() {
		super(false, 0, null, null);
	}

	@Override
	public UUID getUUID() {
		return UUID.randomUUID();
	}

	@Override
    @SideOnly(Side.CLIENT)
	protected void registerIcons(TextureMap itemMap) {
		itemIcon = Items.enchanted_book.getIconFromDamage(0); //TODO
	}

	@Override
	public String getUnlocalizedName(int metadata) {
		return "801.tool.wrench";
	}

	@Override
	public boolean isBlockAllowedToRotate(Block block, int metadata) {
		return true;
	}

	@Override
	public boolean isItemAllowedToPlaceTorches() {
		return false;
	}

	@Override
	protected boolean isActualTool() {
		return false;
	}

	@Override
	public List<String> addInformation(ItemStack stack, EntityPlayer player, boolean par4) {
		return null;
	}

	@Override
	public boolean onItemUse(ITool tool, ItemStack stack, World world, ChunkCoordinates coords, Block block, TileEntity tile, EntityPlayer player, int side) {
		return ItemHelper.onIToolUse(tool, stack, world, coords, block, tile, player, side);
	}

}
