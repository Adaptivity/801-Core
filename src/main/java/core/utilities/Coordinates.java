package core.utilities;

import net.minecraft.util.ChunkCoordinates;

/**
 * Created by Master801 on 12/7/2014 at 8:43 AM.
 * @author Master801
 */
public class Coordinates {

    private int xCoord = -1;
    private int yCoord = -1;
    private int zCoord = -1;

    public Coordinates(int xCoord, int yCoord, int zCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public int getZ() {
        return zCoord;
    }

    public void setX(int newX) {
        xCoord = newX;
    }

    public void setY(int newY) {
        yCoord = newY;
    }

    public void setZ(int newZ) {
        zCoord = newZ;
    }

    public void setCoordinates(int newX, int newY, int newZ) {
        xCoord = newX;
        yCoord = newY;
        zCoord = newZ;
    }

    /**
     * Removed in 1.8.
     */
    @Deprecated
    public ChunkCoordinates toChunkCoordinates() {
        return new ChunkCoordinates(getX(), getY(), getX());
    }

}
