package core.client.renderer.tileentity;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import core.Core;
import core.common.integration.othermods.modelloader.glloader.GLModel;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;

@Deprecated
public abstract class TileEntityObjRendererModelBase extends TileEntityRendererCoreBase {

    /**
     * Bind the model's texture in this method.
     */
    protected abstract void bindTexture(TileEntity tile, double xCoord, double yCoord, double zCoord, float f);

    protected abstract GLModel getObjModel(TileEntity tile);

    @Override
    protected void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
        GL11.glPushMatrix();
        GL11.glTranslatef(new Double(xCoord).floatValue() + 0.5F, new Double(yCoord).floatValue() + 0.5F, new Double(zCoord).floatValue() + 0.5F);
        bindTexture(tile, xCoord, yCoord, zCoord, f);
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        if (doesRenderModel(tile, xCoord, yCoord, zCoord, f)) {
            doesRenderModel(tile, xCoord, yCoord, zCoord, f);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
            return;
        }
        if (getObjModel(tile) != null) {
            getObjModel(tile).render();
        } else {
            LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.WARN, "The Wavefront model is null. :(");
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /**
     * You should only use this method if you don't use an GLModel.
     *
     * Render the model in this method, then return true.
     */
    protected boolean doesRenderModel(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
        return false;
    }

}
