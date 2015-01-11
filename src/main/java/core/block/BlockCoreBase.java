package core.block;

import core.Core;
import core.api.block.IRedstone;
import core.api.block.IRender;
import core.api.client.renderer.block.IBlockRender;
import core.api.client.renderer.block.IInventoryRender;
import core.api.common.IGUId;
import core.api.tileentity.*;
import core.common.resources.CoreResources;
import core.helpers.*;
import core.common.resources.CoreEnums.LoggerEnum;
import core.common.resources.CoreEnums.MiningLevelHelper;
import core.common.resources.CoreEnums.MiningToolHelper;
import core.helpers.WrapperHelper.WrappingData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.UUID;

/**
 * @author Master801
 */
public abstract class BlockCoreBase extends BlockContainer implements IGUId {

    @SideOnly(Side.CLIENT)
    protected abstract void registerIcon(TextureMap map);

    /**
     * Do not remove this method.
     * This for setting the unlocalized name.
     * Which you actually can't set in the constructor. :\
     * This may seem redundant, but it's worth it.
     */
    public abstract String getAdjustedUnlocalizedName();

    public abstract TileEntity createTileEntity(int metadata);

    protected final boolean doesHaveSubtypes;

    /**
     * @param doesHaveSubtypes {@link #getAmountOfSubtypes()}
     */
    protected BlockCoreBase(Material material, boolean doesHaveSubtypes) {
        super(material);
        this.doesHaveSubtypes = doesHaveSubtypes;
        setCreativeTab(CoreResources.getCoreCreativeTab());
        setHardness(2.0F);
    }

    @Override
    public void breakBlock(World world, int xCoord, int yCoord, int zCoord, Block block, int par6) {
        if (WorldHelper.isClient(world)) {
            return;
        }
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof IInventory) {
            IInventory inventory = (IInventory)tile;
            for (int j1 = 0; j1 < inventory.getSizeInventory(); ++j1) {
                ItemStack itemstack = inventory.getStackInSlot(j1);
                if (itemstack != null) {
                    float f = CoreResources.getRandom().nextFloat() * 0.8F + 0.1F;
                    float f1 = CoreResources.getRandom().nextFloat() * 0.8F + 0.1F;
                    float f2 = CoreResources.getRandom().nextFloat() * 0.8F + 0.1F;
                    while (itemstack.stackSize > 0) {
                        int k1 = CoreResources.getRandom().nextInt(21) + 10;
                        if (k1 > itemstack.stackSize) {
                            k1 = itemstack.stackSize;
                        }
                        itemstack.stackSize -= k1;
                        EntityItem entityitem = new EntityItem(world, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));
                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }
                        entityitem.motionX = CoreResources.getRandom().nextGaussian() * 0.05F;
                        entityitem.motionY = CoreResources.getRandom().nextGaussian() * 0.05F + 0.2F;
                        entityitem.motionZ = CoreResources.getRandom().nextGaussian() * 0.05F;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }
            world.func_147453_f(xCoord, yCoord, zCoord, block);
        }
        super.breakBlock(world, xCoord, yCoord, zCoord, block, par6);
    }

    @Override
    public int damageDropped(int damage) {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final void getSubBlocks(Item item, CreativeTabs tab, List stupidList) {
        List<ItemStack> list = (List<ItemStack>)stupidList;
        if (getAmountOfSubtypes() > 0 && doesHaveSubtypes) {
            for(int i = 0; i < (getAmountOfSubtypes()); i++) {
                if (!list.contains(new ItemStack(this, 1, i))) {
                    if (i < 0) {
                        return;
                    }
                    list.add(new ItemStack(this, 1, i));
                } else {
                    LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.ERROR, "List already contains a value for BlockCoreBase while trying to getSubBlocks!");
                    return;
                }
            }
        } else if (addBlocksToCreativeTab() != null) {
            list.addAll(addBlocksToCreativeTab());
        } else {
            super.getSubBlocks(item, tab, list);
        }
    }

    /**
     * Override this for use of subtypes. Acknowledging however, that you've also enabled {@link #doesHaveSubtypes}.
     */
    protected int getAmountOfSubtypes() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return getRenderID();
    }

    @SideOnly(Side.CLIENT)
    public int getRenderID() {
        if (this instanceof IBlockRender) {
            return Core.proxy.getRenderIDFromSpecialID("rotation");
        }
        if (this instanceof IInventoryRender) {
            return Core.proxy.getRenderIDFromSpecialID("inventoryRenderer");
        }
        if (this instanceof IRender) {
            IRender render = (IRender)this;
            return ProxyHelper.getRenderIDFromSpecialID(ProxyHelper.getSidedProxyFromMod(render.getMod(), Side.CLIENT), render.getSpecialRenderID());
        }
        if (isCustomBlock()) {
            return -1;
        }
        return super.getRenderType();
    }

    @Override
    public boolean onBlockActivated(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof IOwner) {
            IOwner ownedTile = (IOwner)tile;
            if (ownedTile.getOwnerUserName() != null) {
                if (!player.getGameProfile().getName().equalsIgnoreCase(ownedTile.getOwnerUserName())) {
                    PlayerHelper.addChatMessage(world, player, ChatHelper.colourString(EnumChatFormatting.DARK_RED, "You tried touching someone else's block! Shame on you!"));
                    return true;
                }
            }
            if (ownedTile.getOwnerUUID() != null) {
                if (ownedTile.getOwnerUUID() != player.getGameProfile().getId()) {
                    PlayerHelper.addChatMessage(world, player, ChatHelper.colourString(EnumChatFormatting.DARK_RED, "You tried touching someone else's block! Shame on you!"));
                    return true;
                }
            }
        }
        if (tile instanceof IActivate) {
            return ((IActivate)tile).onActivated(player, RotationHelper.convertIntToForgeDirection(side));
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return createTileEntity(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registry) {
        registerIcon(TextureHelper.getTextureMap(registry));
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getBlockTexture(IBlockAccess world, int xCoord, int yCoord, int zCoord, int side) {
        return getIcon(side, world.getBlockMetadata(xCoord, yCoord, zCoord));
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getBlockTexture(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        return getBlockTexture(world, xCoord, yCoord, zCoord, RotationHelper.convertForgeDirectionToInt(side));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int xCoord, int yCoord, int zCoord, int side) {
        return getBlockTexture(world, xCoord, yCoord, zCoord, side);
    }

    @Override
    public boolean rotateBlock(World world, int xCoord, int yCoord, int zCoord, ForgeDirection direction) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof IRotatable) {
            IRotatable rotatable = (IRotatable)tile;
            if (rotatable.canBlockRotate()) {
                if (direction.equals(ForgeDirection.UNKNOWN)) {
                    return false;
                }
                rotatable.setRotation(RotationHelper.convertForgeDirectionToByte(direction));
                return true;
            }
        }
        return super.rotateBlock(world, xCoord, yCoord, zCoord, direction);
    }

    @Override
    public ForgeDirection[] getValidRotations(World world, int xCoord, int yCoord, int zCoord) {
        if (world.getTileEntity(xCoord, yCoord, zCoord) instanceof IRotatable) {
            return ForgeDirection.VALID_DIRECTIONS;
        }
        return super.getValidRotations(world, xCoord, yCoord, zCoord);
    }

    @Override
    public void onBlockPlacedBy(World world, int xCoord, int yCoord, int zCoord, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, xCoord, yCoord, zCoord, entity, stack);
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (tile instanceof IOwner) {
                ((IOwner)tile).setOwnerName(player.getGameProfile().getName());
                ((IOwner)tile).setOwnerUUID(player.getGameProfile().getId());
            }
            if (tile instanceof IRotatable) {
                ((IRotatable)tile).setRotation(RotationHelper.calculateBlockRotation(world, xCoord, yCoord, zCoord, player));
            }
        }
    }

    public final Block setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
        return this;
    }

    public final Block setHarvestLevel(MiningToolHelper tool, MiningLevelHelper level) {
        setHarvestLevel(tool.getTool(), level.getMiningLevel());
        return this;
    }

    public final Block setHarvestLevel(MiningToolHelper tool, MiningLevelHelper level, int metadata) {
        setHarvestLevel(tool.getTool(), level.getMiningLevel(), metadata);
        return this;
    }

    public final Block setBlockBounds(float[] bounds) {
        super.setBlockBounds(bounds[0], bounds[1], bounds[2], bounds[3], bounds[4], bounds[5]);
        return this;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int xCoord, int yCoord, int zCoord) {
        AxisAlignedBB axisAlignedBB = getAxisAlignedBBBoxFromPool(world, xCoord, yCoord, zCoord);
        if (axisAlignedBB != null) {
            return axisAlignedBB;
        }
        return super.getCollisionBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int xCoord, int yCoord, int zCoord) {
        AxisAlignedBB axisAlignedBB = getAxisAlignedBBBoxFromPool(world, xCoord, yCoord, zCoord);
        if (axisAlignedBB != null) {
            return axisAlignedBB;
        }
        return super.getSelectedBoundingBoxFromPool(world, xCoord, yCoord, zCoord);
    }

    /**
     * This is a combination of {@link #getCollisionBoundingBoxFromPool(World, int, int, int)}, and {@link #getSelectedBoundingBoxFromPool(World, int, int, int)}.
     */
    public AxisAlignedBB getAxisAlignedBBBoxFromPool(World world, int xCoord, int yCoord, int zCoord) {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return getIcon(RotationHelper.convertIntToForgeDirection(side), metadata);
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getIcon(ForgeDirection side, int metadata) {
        return super.getIcon(RotationHelper.convertForgeDirectionToInt(side), metadata);
    }

    @SideOnly(Side.CLIENT)
    protected List<ItemStack> addBlocksToCreativeTab() {
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube() {
        return isCustomBlock() ? false : super.isOpaqueCube();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean renderAsNormalBlock() {
        return isCustomBlock() ? false : super.renderAsNormalBlock();
    }

    protected boolean isCustomBlock() {
        return false;
    }

    public boolean doesUseWrappingData() {
        return false;
    }

    public WrappingData getWrappingData(int metadata) {
        return null;
    }

    @Override
    public String getUnlocalizedName() {
        return getAdjustedUnlocalizedName();
    }

    @Override
    public UUID getUUID() {
        return UUID.randomUUID();
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof ISolidSideHook) {
            return ((ISolidSideHook)tile).isSideSolid(side);
        }
        return super.isSideSolid(world, xCoord, yCoord, zCoord, side);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, int xCoord, int yCoord, int zCoord, int otherX, int otherY, int otherZ) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        TileEntity otherTile = world.getTileEntity(otherX, otherY, otherZ);
        if (tile instanceof INeighborChangedHook) {
            ((INeighborChangedHook)tile).onNeighborTileChange(otherTile);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int xCoord, int yCoord, int zCoord, Block neighbourBlock) {
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (tile instanceof INeighborChangedHook) {
            ((INeighborChangedHook)tile).onNeighborBlockChange(neighbourBlock);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int xCoord, int yCoord, int zCoord, AxisAlignedBB axis, List list, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            addCollisionBoxesToList(world, xCoord, yCoord, zCoord, axis, (List<AxisAlignedBB>)list, (EntityLivingBase)entity);
        }
    }

    protected void addCollisionBoxesToList(World world, int xCoord, int yCoord, int zCoord, AxisAlignedBB axis, List<AxisAlignedBB> list, EntityLivingBase entity) {
        super.addCollisionBoxesToList(world, xCoord, yCoord, zCoord, axis, list, entity);
    }

    @Override
    public boolean canProvidePower() {
        return this instanceof IRedstone;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int xCoord, int yCoord, int zCoord, int side) {
        return isProvidingWeakPower(world, xCoord, yCoord, zCoord, RotationHelper.convertIntToForgeDirection(side));
    }

    protected int isProvidingWeakPower(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        Block block = world.getBlock(xCoord, yCoord, zCoord);
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (block instanceof IRedstone && tile instanceof IRedstoneControl) {
            return ((IRedstoneControl)tile).getWeakPower(side);
        }
        return super.isProvidingStrongPower(world, xCoord, yCoord, zCoord, RotationHelper.convertForgeDirectionToInt(side));
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int xCoord, int yCoord, int zCoord, int side) {
        return isProvidingStrongPower(world, xCoord, yCoord, zCoord, RotationHelper.convertIntToForgeDirection(side));
    }

    protected int isProvidingStrongPower(IBlockAccess world, int xCoord, int yCoord, int zCoord, ForgeDirection side) {
        Block block = world.getBlock(xCoord, yCoord, zCoord);
        TileEntity tile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (block instanceof IRedstone && tile instanceof IRedstoneControl) {
            return ((IRedstoneControl)tile).getStrongPower(side);
        }
        return super.isProvidingStrongPower(world, xCoord, yCoord, zCoord, RotationHelper.convertForgeDirectionToInt(side));
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int xCoord, int yCoord, int zCoord) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);//Full cube mode.
    }

}
