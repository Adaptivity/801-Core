package core.helpers;

import core.Core;
import core.api.item.IFly;
import core.api.item.tool.ITool;
import core.api.tileentity.IRotatable;
import core.common.resources.CoreEnums.LoggerEnum;
import core.event.items.EventRotation;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Set;

/**
 * @author Master801
 */
public final class ItemHelper {

    public static Set<Block> getPickaxeBlocksEffectiveAgainstField() {
        return MinecraftObfuscationHelper.getMinecraftFieldValue(ItemPickaxe.class, "field_150915_c", "field_150915_c");
    }

    public static boolean onIToolUse(ITool tool, ItemStack stack, World world, ChunkCoordinates coords, Block block, TileEntity tile, EntityPlayer player, int side) {
        if (MinecraftForge.EVENT_BUS.post(new EventRotation.Pre(stack, block, tool))) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Event canceled the rotation for the block. Block: '%s'.", block.getUnlocalizedName());
            return false;
        }
        if (tile instanceof TileEntitySkull) {
            TileEntitySkull skull = (TileEntitySkull)tile;
            int skullRotation = skull.func_145906_b();
            if (player.isSneaking()) {
                if (skullRotation > -1) {
                    skull.func_145903_a(skullRotation + 1);
                }
                if (skullRotation == 15) {
                    skull.func_145903_a(skullRotation - 15);
                }
                if (!player.isSneaking() && !(skull.func_152108_a() == player.getGameProfile())) {
                    skull.func_152106_a(player.getGameProfile());
                }
                if (player.isSneaking() && player.isWet()) { //TODO
                    skull.func_152106_a(null);
                }
                world.markBlockForUpdate(coords.posX, coords.posY, coords.posZ);
                return true;
            }
        }
        if (tile instanceof IRotatable) {
            IRotatable rotatable = (IRotatable)tile;
            if (rotatable.getRotation() >= 5) {
                rotatable.setRotation(new Integer(0).byteValue());
            } else {
                rotatable.setRotation(new Integer(rotatable.getRotation() + 1).byteValue());
            }
            return true;
        }
        if (tool.isBlockAllowedToRotate(block, world.getBlockMetadata(coords.posX, coords.posY, coords.posZ))) {
            block.rotateBlock(world, coords.posX, coords.posY, coords.posZ, ForgeDirection.getOrientation(side));
            return true;
        }
        MinecraftForge.EVENT_BUS.post(new EventRotation.Post(stack, block, tool));
        return false;
    }

    public static IFly onIFlyRightClicked(ItemStack stack, EntityPlayer player) {
        if (stack != null) {
            if (stack.getItem() instanceof IFly) {
                do {
                    if (stack.getTagCompound() == null) {
                        stack.setTagCompound(new NBTTagCompound());
                        stack.getTagCompound().setBoolean("isAlreadyActivated", false);
                    }
                    if (player.isSneaking()) {
                        if (stack.getTagCompound().getBoolean("isAlreadyActivated")) {
                            stack.getTagCompound().setBoolean("isAlreadyActivated", false);
                        } else {
                            stack.getTagCompound().setBoolean("isAlreadyActivated", true);
                        }
                        player.capabilities.allowFlying = stack.getTagCompound().getBoolean("isAlreadyActivated");
                    }
                } while (player.inventory.hasItemStack(stack));
                if (!player.inventory.hasItemStack(stack) && stack.getTagCompound().getBoolean("isAlreadyActivated")) {
                    player.capabilities.allowFlying = false;
                    stack.setTagCompound(null);
                }
                return (IFly)stack.getItem();
            }
        }
        return null;
    }

    public static IFly addInformationToIFly(IFly fly, NBTTagCompound nbt, List<String> list) {
        if (nbt == null) {
            list.add("Can Fly: " + ChatHelper.colourString(EnumChatFormatting.RED, "false"));
        } else {
            boolean activated = nbt.getBoolean("isAlreadyActivated");
            list.add("Can Fly: " + (activated ? ChatHelper.colourString(EnumChatFormatting.AQUA, true) : ChatHelper.colourString(EnumChatFormatting.RED, false)));
        }
        return fly;
    }

    public static IFly onIFlyCreated(ItemStack stack) {
        if (stack != null) {
            if (stack.getItem() instanceof IFly) {
                if (stack.getTagCompound() == null) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                stack.getTagCompound().setBoolean("isAlreadyActivated", false);
                return (IFly)stack.getItem();
            }
        }
        return null;
    }

    /**
     * This gets or creates the ItemStacks's tag compound.
     */
    public static NBTTagCompound getItemStackTagCompound(ItemStack stack) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null) {
            nbt = new NBTTagCompound();
            stack.setTagCompound(nbt);
        }
        return nbt;
    }

}
