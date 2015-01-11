package core.client.texture.sprites;

import java.util.List;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import core.api.client.texture.sprite.ISprite;
import core.helpers.MinecraftObfuscationHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This Class should <b> never </b> have an abstract modifier.
 * @author Master801
 */
public class SpriteAtlasCoreBase extends TextureAtlasSprite {

	protected SpriteAtlasCoreBase(String spriteName) {
		super(spriteName);
	}

	/**
	 * Sets the Sprite upside down.
	 */
	public final SpriteAtlasCoreBase renderSpriteUpsideDown() {
		MinecraftObfuscationHelper.setMinecraftFieldValue(TextureAtlasSprite.class, this, "field_110978_o", "maxV", getMinV()); //FIXME No idea if this works or not
		MinecraftObfuscationHelper.setMinecraftFieldValue(TextureAtlasSprite.class, this, "field_110979_l", "minV", getMaxV()); //FIXME No idea if this works or not
		rotated = true;
		return this;
	}

	protected final void resetSprite() {
		MinecraftObfuscationHelper.getMinecraftMethodValue(TextureAtlasSprite.class, this, "func_130102_n", "resetSprite", MinecraftObfuscationHelper.createNewParameterArray(), new Object[0]);
	}

	protected final void fixTransparentPixels(int[][] array) {
		MinecraftObfuscationHelper.invokeMinecraftMethod(TextureAtlasSprite.class, this, "func_147961_a", "fixTransparentPixels", MinecraftObfuscationHelper.createNewParameterArray(int[][].class), new Object[] { array });
	}

	protected final int[][] prepareAnisotropicFiltering(int[][] array, int par2, int par3) {
		return (int[][])MinecraftObfuscationHelper.getMinecraftMethodValue(TextureAtlasSprite.class, this, "func_147960_a", "prepareAnisotropicFiltering", MinecraftObfuscationHelper.createNewParameterArray(int[][].class, int.class, int.class), array, par2, par3);
	}

	protected final void allocateFrameTextureData(int par1) {
		MinecraftObfuscationHelper.invokeMinecraftMethod(TextureAtlasSprite.class, this, "func_130099_d", "allocateFrameTextureData", MinecraftObfuscationHelper.createNewParameterArray(int.class), par1);
	}

	protected final boolean getFieldUseAnisotropicFiltering() {
		return MinecraftObfuscationHelper.getMinecraftFieldValue(TextureAtlasSprite.class, this, "field_147966_k", "useAnisotropicFiltering");
	}

	protected final void setFieldUseAnisotropicFiltering(boolean useAnisotropicFiltering) {
		MinecraftObfuscationHelper.setMinecraftFieldValue(TextureAtlasSprite.class, this, "field_147966_k", "useAnisotropicFiltering", useAnisotropicFiltering);
	}

	protected final AnimationMetadataSection getAnimationMetadata() {
		return (AnimationMetadataSection)MinecraftObfuscationHelper.getMinecraftFieldValue(TextureAtlasSprite.class, this, "field_110982_k", "animationMetadata");
	}

	protected final void setAnimationMetadata(AnimationMetadataSection metadata) {
		MinecraftObfuscationHelper.setMinecraftFieldValue(TextureAtlasSprite.class, this, "field_110982_k", "animationMetadata", metadata);
	}

	public final List<int[][]> getFramesTextureData() {
		return MinecraftObfuscationHelper.getMinecraftFieldValue(TextureAtlasSprite.class, this, "field_110976_a", "framesTextureData");
	}

	/**
	 * @return This is really just only a boolean. -.-;
	 */
	@Override
	public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
		if (this instanceof ISprite) {
            ISprite iSprite = (ISprite)this;
            if (!iSprite.doesUseResourceLocation()) {
                return true;
            } else {
                return false;
            }
		}
		return super.hasCustomLoader(manager, location);
	}

	/**
	 * Does the exact same thing as it normally does.
	 * Except, this method doesn't replace the List, but instead clears it, then adds the new data back in the list.
	 */
	@Override
	public void generateMipmaps(int mipmapLevel) {
		int[][] mipmapData = null;
		for(int i = 0; i < getFramesTextureData().size(); i++) {
			mipmapData = TextureUtil.generateMipmapData(mipmapLevel, width, getFramesTextureData().get(i));
            getFramesTextureData().clear();
            getFramesTextureData().add(mipmapData);
		}
	}

	public final ISprite getISprite() {
		if (this instanceof ISprite) {
			return ((ISprite)this);
		}
		return null;
	}

}
