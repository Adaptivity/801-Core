package core.client.renderer.tileentity.test;

import core.client.renderer.tileentity.TileEntityRendererWavefrontCoreBase;
import core.helpers.TextureHelper;
import core.helpers.WavefrontModelHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Created by Master801 on 12/14/2014 at 1:42 PM.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class TileEntityRendererWavefrontTest extends TileEntityRendererWavefrontCoreBase {

    @Override
    protected TextureHelper.TextureUsageInfo getTextureInfo(int metadata) {
        return new TextureHelper.TextureUsageInfo(false, "/resources/textures/models/test/Test.png");
    }

    @Override
    public WavefrontModelHelper.ModelWavefront getWavefrontModel() {
        return WavefrontModelHelper.loadWavefrontModel("/resources/models/Test.obj");
    }

}
