package core.helpers;

import core.Core;
import core.api.client.texture.sprite.ISpriteCustom;
import core.client.texture.sprites.SpriteAtlasCoreBase;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions;
import core.common.resources.CoreEnums.LoggerEnum;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Master801 on 10/19/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class SpriteHelper {

    private static final Map<String, Map<String, TextureAtlasSprite>> SPRITE_SHEET_ATLAS_MAPPING = new HashMap<String, Map<String, TextureAtlasSprite>>();
    private static final Map<String, TextureAtlasSprite> CUSTOM_SPRITE_ATLAS_MAPPING = new HashMap<String, TextureAtlasSprite>();

    public static TextureAtlasSprite createNewSpriteSheetAtlas(String sheetPath, String spriteName, IIconRegister iconRegister, int x, int y) {
        return createNewSpriteSheetAtlas(sheetPath, spriteName, iconRegister, x, y, 16);
    }

    public static TextureAtlasSprite createNewSpriteSheetAtlas(String sheetPath, String spriteName, IIconRegister iconRegister, int x, int y, int spriteDimensions) {
        if (sheetPath == null) {
            return null;
        }
        if (spriteName == null) {
            return null;
        }
        if (iconRegister == null) {
            return null;
        }
        TextureMap textureMap = TextureHelper.getTextureMap(iconRegister);
        Map<String, TextureAtlasSprite> spritesheetMap = SPRITE_SHEET_ATLAS_MAPPING.get(sheetPath);
        if (spritesheetMap == null) {
            spritesheetMap = new HashMap<String, TextureAtlasSprite>();
        }
        TextureAtlasSprite sprite = spritesheetMap.get(spriteName);
        TextureAtlasSprite mapSprite = textureMap.getTextureExtry(spriteName);
        if (mapSprite != null) {
            return mapSprite;
        }
        InputStream stream = ResourcePackHelper.getInputStreamFromCurrentPack(sheetPath);
        if (stream == null) {
            stream = InputStreamHelper.getStreamFromResource(sheetPath, false);
        }
        BufferedImage sheet = ImageHelper.getImageFromStream(stream);
        int dividedX = sheet.getWidth() / spriteDimensions;
        int dividedY = sheet.getHeight() / spriteDimensions;
        BufferedImage cutSprite = sheet.getSubimage(x * dividedX, y * dividedY, spriteDimensions, spriteDimensions);
        if (sprite == null) {
            sprite = new CustomSpriteAtlas(spriteName, cutSprite);
        }
        textureMap.setTextureEntry(spriteName, sprite);
        SPRITE_SHEET_ATLAS_MAPPING.put(sheetPath, spritesheetMap);
        LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Added Sprite from a custom sheet, to the internal sheet. Sheet-Path: '%s', Sprite-Name: '%s'", sheetPath, spriteName);
        return sprite;
    }

    public static TextureAtlasSprite createNewCustomSpriteAtlas(String sheetPath, String spriteName, IIconRegister iconRegister){
        return SpriteHelper.createNewCustomSpriteAtlas(sheetPath, false, spriteName, iconRegister);
    }

    public static TextureAtlasSprite createNewCustomSpriteAtlas(ResourceLocation sheetPath, String spriteName, IIconRegister registry) {
        return SpriteHelper.createNewCustomSpriteAtlas("/assets/" + sheetPath.getResourceDomain() + sheetPath.getResourcePath(), true, spriteName, registry);
    }

    /**
     * Only used internally.
     */
    private static TextureAtlasSprite createNewCustomSpriteAtlas(String sheetPath, boolean doesUseResourceLocation, String spriteName, IIconRegister iconRegister) {
        if (sheetPath == null) {
            return null;
        }
        if (spriteName == null) {
            return null;
        }
        if (iconRegister == null) {
            return null;
        }
        TextureMap textureMap = TextureHelper.getTextureMap(iconRegister);
        InputStream stream = null;
        if (doesUseResourceLocation) {
            try {
                stream = CoreResources.getResourceManager().getResource(new ResourceLocation(sheetPath)).getInputStream();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            stream = InputStreamHelper.getStreamFromResource(sheetPath, false);
        }
        BufferedImage image = ImageHelper.getImageFromStream(stream);
        TextureAtlasSprite customSprite = CUSTOM_SPRITE_ATLAS_MAPPING.get(spriteName);
        if (customSprite == null && image != null) {
            customSprite = new CustomSpriteAtlas(spriteName, image);
        } else if (image == null) {
            throw new CoreExceptions.CoreNullPointerException("Could not find the sprite-sheet file! Supposed sprite-sheet path: '%s'", sheetPath);
        }
        textureMap.setTextureEntry(spriteName, customSprite);
        CUSTOM_SPRITE_ATLAS_MAPPING.put(spriteName, customSprite);
        LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Added a Custom Sprite to Minecraft's internal sprite-sheet. Sprite-sheet path: '%s', Sprite-name: '%s'", sheetPath, spriteName);
        return customSprite;
    }

    /**
     * Will return null if either the sprite-sheet's name is not valid, or the sprite's name is not valid too.
     */
    public static TextureAtlasSprite getRegisteredSpriteSheetAtlas(String spritesheetName, String spriteName) {
        Map<String, TextureAtlasSprite> sheetMap = SPRITE_SHEET_ATLAS_MAPPING.get(spritesheetName);
        if (sheetMap == null) {
            return null;
        }
        TextureAtlasSprite sprite = sheetMap.get(spriteName);
        if (sprite == null) {
            return null;
        }
        return sprite;
    }

    public static TextureAtlasSprite getRegisteredCustomSpriteAtlas(String spriteName) {
        return CUSTOM_SPRITE_ATLAS_MAPPING.get(spriteName);//Not my problem if the sprite is null.
    }

    /**
     * @author Master801
     */
    private static class CustomSpriteAtlas extends SpriteAtlasCoreBase implements ISpriteCustom {

        private final BufferedImage image;

        /**
         * @param image The sprite's BufferedImage should already be cut.
         */
        CustomSpriteAtlas(String spriteName, BufferedImage image) {
            super(spriteName);
            this.image = image;
            width = image.getWidth();
            height = image.getHeight();
        }

        @Override
        public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {
            int[][] array = new int[CoreResources.getGameOptions().mipmapLevels + 1][];
            for(int i = 0; i < array.length; i++) {
                array[i] = getRGBArray();
            }
            getFramesTextureData().add(array);
            return false;
        }

        @Override
        public boolean doesUseResourceLocation() {
            return false;
        }

        @Override
        public BufferedImage getBufferedImage() {
            return image;
        }

        @Override
        public int[] getRGBArray() {
            return getBufferedImage().getRGB(0, 0, getIconWidth(), getIconHeight(), new int[getIconWidth() * getIconHeight()], 0, getIconWidth());
        }

        @Override
        public void updateAnimation() {
            //Do nothing.
        }

        @Override
        public void generateMipmaps(int mipmapLevel) {
            if (getRGBArray() != null) {
                for (int i = 0; i < getFramesTextureData().size(); i++) {
                    int[][] mipmapData = new int[getFramesTextureData().size()][];
                    mipmapData[i] = getRGBArray();
                    mipmapData = prepareAnisotropicFiltering(mipmapData, getIconWidth(), getIconHeight());
                    getFramesTextureData().clear();
                    getFramesTextureData().add(mipmapData);
                }
            } else {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The RGB array is null! Sprite: '%s'", getIconName());
            }
        }

    }

    /**
     * WIP Class.
     * @author Master801
     */
    private static final class CustomAnimatedSpriteAtlas extends CustomSpriteAtlas {//TODO

        CustomAnimatedSpriteAtlas(String spriteName, BufferedImage image) {
            super(spriteName, image);
        }

        @Override
        public void updateAnimation() {
            //Do nothing.
        }

    }

}
