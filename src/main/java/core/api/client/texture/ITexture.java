package core.api.client.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.ITextureObject;

/**
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public interface ITexture extends ITextureObject {

    String getTexturePath();

	int getWidth();

	int getHeight();

}
