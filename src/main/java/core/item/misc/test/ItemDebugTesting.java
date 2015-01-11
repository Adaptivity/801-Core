package core.item.misc.test;

import java.util.List;
import java.util.UUID;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import core.api.item.IFly;
import core.api.item.IPlaceableTorch;
import core.item.ItemCoreBase;

public class ItemDebugTesting extends ItemCoreBase implements IFly, IPlaceableTorch {

	public ItemDebugTesting() {
		super(false);
	}

	@Override
	public UUID getUUID() {
		return UUID.randomUUID();
	}

	@Override
	public boolean doesItemAllowCreativeFlying() {
		return true;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(TextureMap itemMap) {
		itemIcon = itemMap.registerIcon("diamond");
	}

	@Override
	public boolean isItemAllowedToPlaceTorches() {
		return true;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public String getUnlocalizedName(int metadata) {
		return "debugTesting";
	}

	@Override
	public List<String> addInformation(ItemStack stack, EntityPlayer player, boolean par3) {
		return null;
	}

}
