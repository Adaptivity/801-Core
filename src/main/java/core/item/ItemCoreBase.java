package core.item;

import core.api.client.gui.IGuiHelper;
import core.api.common.IGUId;
import core.api.item.IFly;
import core.api.item.IPlaceableTorch;
import core.api.item.armour.IArmour;
import core.common.resources.CoreResources;
import core.helpers.ItemHelper;
import core.helpers.TextureHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Custom item for 801-Core.
 * @author Master801
 */
public abstract class ItemCoreBase extends Item implements IGuiHelper, IGUId {

    @SideOnly(Side.CLIENT)
	public abstract String getUnlocalizedName(int metadata);

    @SideOnly(Side.CLIENT)
    protected abstract void registerIcons(TextureMap textureMap);

    public ItemCoreBase(boolean doesHaveSubtypes) {
        this(doesHaveSubtypes, false);
    }

	public ItemCoreBase(boolean doesHaveSubtypes, boolean full3d) {
		setCreativeTab(CoreResources.CORE_LIBRARY_CREATIVE_TAB); //This is set to the default tab.
        if (full3d) {
            setFull3D();
        }
		if (doesHaveSubtypes) {
			setMaxDamage(0);
			setHasSubtypes(true);
		} else {
			setHasSubtypes(false);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (stack == null) {
            return null;
        }
		Item item = stack.getItem();
		if (item instanceof IFly) {
			ItemHelper.onIFlyRightClicked(stack, player);
		}
		return stack;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int xCoord, int yCoord, int zCoord, int side, float hitX, float hitY, float hitZ) {
		Item item = stack.getItem();
		Block block = world.getBlock(xCoord, yCoord, zCoord);
		if (item instanceof IPlaceableTorch) {
			IPlaceableTorch torchPlacer = (IPlaceableTorch)item;
			InventoryPlayer inventory = player.inventory;
            //TODO
            return true;
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List stupidList, boolean par4) {
		List<String> list = (List<String>)stupidList;
		Item item = stack.getItem();
		if (item instanceof IFly) {
			ItemHelper.addInformationToIFly(((IFly)item), stack.getTagCompound(), list);
		}
		List<String> information = addInformation(stack, player, par4);
		if (information != null) {
			list.addAll(information);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List stupidList) {
		List<ItemStack> list = (List<ItemStack>)stupidList;
		if (getSubItems(tab) != null) {
			list.addAll(getSubItems(tab));
			return;
		}
		super.getSubItems(item, tab, list);
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		super.onCreated(stack, world, player);
        if (stack == null) {
            return;
        }
		Item item = stack.getItem();
		if (item instanceof IFly) {
			ItemHelper.onIFlyCreated(stack);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getUnlocalizedName(ItemStack stack) {
		if (getUnlocalizedName(stack.getItemDamage()) != null) {
			return "item." + getUnlocalizedName(stack.getItemDamage()) + ".name";
		}
		return super.getUnlocalizedName(stack) + ".name";
	}

	@Override
	public boolean isItem() {
		return true;
	}

	@Override
	public boolean doesHaveGui() {
		return false;
	}

	@Override
	public Container getServerGui(InventoryPlayer inventory) {
		return null;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public GuiContainer getClientGui(InventoryPlayer inventory) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	protected List<ItemStack> getSubItems(CreativeTabs tab) {
		return null;
	}

	@Override
    @SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
        registerIcons(TextureHelper.getTextureMap(registry));
	}

	@SideOnly(Side.CLIENT)
	protected List<String> addInformation(ItemStack stack, EntityPlayer player, boolean par3) {
		return null;
	}

	@Override
	public UUID getUUID() {
		return UUID.randomUUID();
	}

    @Override
    public boolean isValidArmor(ItemStack stack, int armourType, Entity entity) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() == null) {
            return false;
        }
        if (stack.getItem() instanceof IArmour) {
            return armourType == ((IArmour) stack.getItem()).getArmourType(stack.getItemDamage()).getSlotNumber();
        }
        return false;
    }

}
