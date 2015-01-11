package core.helpers;

import core.Core;
import core.common.resources.CoreEnums.LoggerEnum;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Based-heavily off of my BluePower 'RendererHelper' class file.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class BlockRendererHelper {

	/**
	 * Make sure you have already pushed the gl matrix, or else this won't work.
	 */
	public static void renderBlockAsItem(RenderBlocks renderer, Tessellator tessellator, Block block, int metadata) {
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));//Bottom
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));//Top
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));//East
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));//West
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));//North
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));//South
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	public static boolean renderBlockColours(RenderBlocks renderer, Block block, int metadata, int xCoord, int yCoord, int zCoord) {
		ChunkCoordinates coords = new ChunkCoordinates(xCoord, yCoord, zCoord);
		switch(metadata) {
		case 0:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.WHITE); //White
		case 1:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.ORANGE); //Orange
		case 2:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.MAGENTA); //Magenta
		case 3:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, ColourHelper.LIGHT_BLUE); //LightBlue
		case 4:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.YELLOW); //Yellow
		case 5:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, ColourHelper.LIME_GREEN); //Lime
		case 6:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.PINK); //Pink
		case 7:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.GRAY); //Gray
		case 8:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, ColourHelper.SILVER); //Silver
		case 9:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.CYAN); //Cyan
		case 10:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, ColourHelper.PURPLE); //Purple
		case 11:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.BLUE); //Blue
		case 12:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, ColourHelper.BROWN); //Brown
		case 13:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.GREEN); //Green
		case 14:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.RED); //Red
		case 15:
			return BlockRendererHelper.renderStandardBlockWithColourMultiplyer(renderer, block, coords, Color.BLACK); //Black
		default:
			return false;
		}
	}

	public static boolean renderStandardBlockWithColourMultiplyer(RenderBlocks renderer, Block block, ChunkCoordinates coords, Color colour) {
		return renderer.renderStandardBlockWithColorMultiplier(block, coords.posX, coords.posY, coords.posZ, colour.getRed(), colour.getGreen(), colour.getBlue());
	}

	public static void renderFace(ForgeDirection direction, RenderBlocks renderer, Block block, ChunkCoordinates coords, IIcon icon) {
		switch(direction) {
		case DOWN:
			renderer.renderFaceYPos(block, coords.posX, coords.posY, coords.posZ, icon);
			break;
		case EAST:
			renderer.renderFaceXPos(block, coords.posX, coords.posY, coords.posZ, icon);
			break;
		case NORTH:
			renderer.renderFaceZNeg(block, coords.posX, coords.posY, coords.posZ, icon);
			break;
		case SOUTH:
			renderer.renderFaceZPos(block, coords.posX, coords.posY, coords.posZ, icon);
			break;
		case UP:
			renderer.renderFaceYNeg(block, coords.posX, coords.posY, coords.posZ, icon);
			break;
		case WEST:
			renderer.renderFaceXNeg(block, coords.posX, coords.posY, coords.posZ, icon);
			break;
		default:
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.FATAL, "Could not render the block's face. :(");
            break;
		}
	}

    public static void resetUVValues(RenderBlocks renderer) {
        renderer.uvRotateNorth = 0;
        renderer.uvRotateSouth = 0;
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;
    }

}
