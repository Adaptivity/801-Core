package core.helpers;

import core.exceptions.CoreExceptions.CoreNullPointerException;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public final class TileEntityHelper {

    /**
     * Never worked anyway...
     */
    @Deprecated
    public static void setTileEntityAsChild(TileEntity parent, TileEntity child) {
        if (parent == null) {
            return;
        }
        if (child == null) {
            return;
        }
        child.xCoord = parent.xCoord;
        child.yCoord = parent.yCoord;
        child.zCoord = parent.zCoord;
        child.setWorldObj(parent.getWorldObj());
        child.blockType = parent.blockType;
        child.blockMetadata = parent.blockMetadata;
    }

    public static TileEntity getTileEntityInDirection(TileEntity tile, ForgeDirection direction) {
        return TileEntityHelper.getTileEntityInDirection(tile.getWorldObj(), new ChunkCoordinates(tile.xCoord, tile.yCoord, tile.zCoord), direction);
    }

    /**
     * Thanks to OpenMods (OpenBlocks team) for most of this code.
     */
    public static TileEntity getTileEntityInDirection(IBlockAccess world, ChunkCoordinates coordinates, ForgeDirection direction) {
        if (coordinates == null) {
            return null;
        }
        if (world == null) {
            return null;
        }
        if (direction == null) {
            return null;
        }
        if (direction.equals(ForgeDirection.UNKNOWN)) {
            throw new CoreNullPointerException("The ForgeDirection is %s!", direction.name().toLowerCase());
        }
        int xCoord = coordinates.posX + direction.offsetX;
        int yCoord = coordinates.posY + direction.offsetY;
        int zCoord = coordinates.posZ + direction.offsetZ;
        TileEntity gotTile = world.getTileEntity(xCoord, yCoord, zCoord);
        if (gotTile != null) {
            return gotTile;
        }
        return null;
    }

    public static Block getBlockInDirection(TileEntity tile, ForgeDirection direction) {
        return TileEntityHelper.getBlockInDirection(tile.getWorldObj(), new ChunkCoordinates(tile.xCoord, tile.yCoord, tile.zCoord), direction);
    }

    public static Block getBlockInDirection(IBlockAccess world, ChunkCoordinates coordinates, ForgeDirection direction) {
        if (world == null) {
            return null;
        }
        if (coordinates == null) {
            return null;
        }
        if (direction == null) {
            return null;
        }
        if (direction.equals(ForgeDirection.UNKNOWN)) {
            throw new CoreNullPointerException("The ForgeDirection is Unknown!");
        }
        int xCoord = coordinates.posX + direction.offsetX;
        int yCoord = coordinates.posY + direction.offsetY;
        int zCoord = coordinates.posZ + direction.offsetZ;
        Block block = world.getBlock(xCoord, yCoord, zCoord);
        if (block != null) {
            return block;
        }
        return null;
    }

    public static void markBlockForUpdate(TileEntity tile) {
        if (tile == null) {
            return;
        }
        tile.getWorldObj().markBlockForUpdate(tile.xCoord, tile.yCoord, tile.zCoord);
    }

}
