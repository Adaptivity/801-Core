package core.client.renderer.tileentity;

import core.Core;
import core.client.renderer.tileentity.TileEntityRendererCoreBase;
import core.common.resources.CoreEnums;
import core.helpers.GLHelper;
import core.helpers.LoggerHelper;
import core.helpers.TextureHelper;
import core.helpers.WavefrontModelHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

/**
 * Created by Master801 on 12/14/2014 at 1:24 PM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class TileEntityRendererWavefrontCoreBase extends TileEntityRendererCoreBase {

    protected abstract TextureHelper.TextureUsageInfo getTextureInfo(int metadata);

    public abstract WavefrontModelHelper.ModelWavefront getWavefrontModel();

    @Override
    protected final void renderTileEntity(TileEntity tile, double xCoord, double yCoord, double zCoord, float f) {
        GLHelper.glColour4f();
        GL11.glTranslated(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D);
        TextureHelper.bindTextureFromInfo(getTextureInfo(tile.getBlockMetadata()));
        GL11.glRotated(180.0D, 0.D, 0.0D, 1.0D);
        GL11.glPushMatrix();
        renderModel(tile, xCoord, yCoord, zCoord);
        if (getGroupsToRender() == null) {
            getWavefrontModel().renderEverything();
        } else {
            for(String group : getGroupsToRender()) {
                if (group == null) {
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.ERROR, "Wavefront group is null... skipping group");
                    continue;
                }
                getWavefrontModel().renderGroup(group);
            }
        }
        GL11.glPopMatrix();
    }

    /**
     * Return null if you want to render all groups.
     * Else return the groups you want to render.
     */
    protected String[] getGroupsToRender() {
        return null;
    }

    /**
     * Used for rotating, translating and doing random things to the model.
     */
    protected void renderModel(TileEntity tile, double xCoord, double yCoord, double zCoord) {
    }

}
