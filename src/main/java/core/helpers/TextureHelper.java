package core.helpers;

import core.Core;
import core.api.client.texture.ITexture;
import core.api.client.texture.ITextureAnimated;
import core.common.resources.CoreResources;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public final class TextureHelper {

    private static final Map<String, ITexture> CUSTOM_TEXTURES_BUFFER_MAP = new HashMap<String, ITexture>();

	/**
	 * Note, this does not close the InputStream.
	 */
	public static BufferedImage getImageFromStream(InputStream stream) {
		if (stream == null) {
			throw new CoreNullPointerException("InputStream is null!");
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
			InputStreamHelper.closeInputStream(stream);
		}
		if (image == null) {
			InputStreamHelper.closeInputStream(stream);
			throw new CoreNullPointerException("BufferedImage is null!");
		}
		return image;
	}

	public static void bindTexture(int textureID) {
		MinecraftObfuscationHelper.invokeMinecraftMethod(TextureUtil.class, "func_94277_a", "bindTexture", MinecraftObfuscationHelper.createNewParameterArray(int.class), textureID);
	}

	/**
	 * Only used for binding a CustomTexture!
	 * 
	 * You should only bind the texture right before whatever you're using as a rendering system.
	 */
	public static ITexture bindTexture(String texturePath) {
        ITexture texture = TextureHelper.createNewCustomTexture(texturePath);
        TextureHelper.bindTexture(texture.getGlTextureId());
        return texture;
	}

    public static void bindAnimatedTexture(TextureAnimationInfo info) {
        ITextureAnimated animatedTexture = new CustomTextureAnimated(info.getTexturePath(), info.doesUseFrameTime(), info.getAmountOfFrames(), info.getFrameTime());
        try {
            animatedTexture.loadTexture(null);
        } catch(Exception exception) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.FATAL, "Caught Exception! Exception: '%s'", exception.toString());
        }
        if (animatedTexture.getGlTextureId() < 0) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The texture's is not valid! Texture Path: '%s'", info.getTexturePath());
            return;
        }
        TextureHelper.bindTexture(animatedTexture.getGlTextureId());
    }

	public static void bindTexture(ITexture texture) {
		if (texture == null) {
			throw new CoreNullPointerException("The ITexture is null!");
		}
		TextureHelper.bindTexture(texture.getGlTextureId());
	}

    public static void bindTextureFromInfo(TextureHelper.TextureUsageInfo textureUsageInfo) {
        if (textureUsageInfo == null) {
            return;
        }
        if (textureUsageInfo.doesUseResourceLocation()) {
            CoreResources.getTextureManager().bindTexture(textureUsageInfo.getResourceLocation());
        } else {
            TextureHelper.bindTexture(textureUsageInfo.getTexturePath());
        }
    }

	public static TextureAtlasSprite getMissingTextureSprite(TextureMap map) {
		return MinecraftObfuscationHelper.getMinecraftFieldValue(TextureMap.class, map, "field_94249_f", "missingImage");
	}

    /**
     * This makes a texture file from the path you give it.
     */
	public static ITexture createNewCustomTexture(String textureFilePath) {
        ITexture texture = TextureHelper.CUSTOM_TEXTURES_BUFFER_MAP.get(textureFilePath);
        if (texture == null) {
            texture = new CustomTexture(textureFilePath);
            try {
                texture.loadTexture(CoreResources.getResourceManager());
                TextureHelper.CUSTOM_TEXTURES_BUFFER_MAP.put(textureFilePath, texture);
            } catch(Exception exception) {
                LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.FATAL, "Caught exception while loading a custom texture! Exception: '%s'", exception.toString());
            }
        }
        return texture;
	}

    public static ITexture createNewCustomTexture(ResourceLocation resourceLocation) {
        return TextureHelper.createNewCustomTexture(RandomHelper.convertResourceLocationToString(resourceLocation));
    }

	/**
	 * @return The Object's TextureMap. (Either the item, or block TextureMap.)
	 */
	public static TextureMap getTextureMap(IIconRegister iiconRegister) {
        if (iiconRegister instanceof TextureMap) {
            return (TextureMap)iiconRegister;
        }
        return null;
	}

	/**
	 * @author Master801
	 */
    @SideOnly(Side.CLIENT)
	private static class CustomTexture extends AbstractTexture implements ITexture {

		private final String texturePath;
		private int width = -1, height = -1;

		/**
		 * Only use if you're trying to get a texture from an external location, and if the texture is <b> not </b> an animation!
		 */
		public CustomTexture(String texturePath) {
			this.texturePath = texturePath;
		}

		@Override
		public void loadTexture(IResourceManager manager) {
			deleteGlTexture();
			InputStream stream = ResourcePackHelper.getInputStreamFromCurrentPack(getTexturePath());
            if (stream == null) {
                stream = InputStreamHelper.getStreamFromResource(getTexturePath(), false);
            }
			BufferedImage bufferedImage = TextureHelper.getImageFromStream(stream);
            if (bufferedImage == null) {
                InputStreamHelper.closeInputStream(stream);
                return;
            }
			width = bufferedImage.getWidth();
			height = bufferedImage.getHeight();
			TextureUtil.uploadTextureImageAllocate(getGlTextureId(), bufferedImage, false, false);
			InputStreamHelper.closeInputStream(stream);
		}

        @Override
        public String getTexturePath() {
            return texturePath;
        }

		@Override
		public int getWidth() {
			return width;
		}

		@Override
		public int getHeight() {
			return height;
		}

    }

    @SideOnly(Side.CLIENT)
    private static final class CustomTextureAnimated extends AbstractTexture implements ITextureAnimated {

        private final String texturePath;
        private final int amountOfFrames, animationFrameTime;
        private final boolean doesUseFrameTime;

        private CustomTextureAnimated() {
            texturePath = "/resources/Used_Default_Constructor.png";
            amountOfFrames = 0;
            animationFrameTime = -1;
            doesUseFrameTime = false;
        }

        /**
         * Only use if you're trying to get a texture from an external location, and if the texture <b>is</b> an animation!
         */
        public CustomTextureAnimated(String texturePath, boolean doesUseFrameTime, int amountOfFrames, int animationFrameTime) {
            this.texturePath = texturePath;
            this.amountOfFrames = amountOfFrames;
            this.doesUseFrameTime = doesUseFrameTime;
            this.animationFrameTime = animationFrameTime;
        }

        @Override
        public void loadTexture(IResourceManager resourceManager) {
            //TODO
        }

        @Override
        public String getTexturePath() {
            return texturePath;
        }

        @Override
        public int getWidth() {
            return 0;//TODO
        }

        @Override
        public int getHeight() {
            return 0;//TODO
        }

        @Override
        public boolean doesUseFrameTime() {
            return doesUseFrameTime;
        }

        @Override
        public int getFrameTime() {
            return animationFrameTime;
        }

        /**
         * Input the frame number to get the frame's gl id.
         */
        @Override
        public Map<Integer, BufferedImage> getFrameID() {
            return null;//TODO
        }

    }

    @SideOnly(Side.CLIENT)
    public static final class TextureAnimationInfo {

        private final String texturePath;
        private final boolean doesUseFrameTime;
        private final int amountOfFrames, animationFrameTime, xSize, ySize;

        public TextureAnimationInfo(String texturePath, boolean doesUseFrameTime, int amountOfFrames, int animationFrameTime, int xSize, int ySize) {
            this.texturePath = texturePath;
            this.doesUseFrameTime = doesUseFrameTime;
            this.amountOfFrames = amountOfFrames;
            this.animationFrameTime = animationFrameTime;
            this.xSize = xSize;
            this.ySize = ySize;
        }

        public String getTexturePath() {
            return texturePath;
        }

        public boolean doesUseFrameTime() {
            return doesUseFrameTime;
        }

        public int getHeight() {
            return ySize;
        }

        public int getWidth() {
            return xSize;
        }

        public int getFrameTime() {
            return animationFrameTime;
        }

        public int getAmountOfFrames() {
            return amountOfFrames;
        }
    }

    /**
     * Created by Master801 on 11/15/2014.
     * @author Master801
     */
    public static class TextureUsageInfo {

        private boolean useResourceLocation = false;
        private String texturePath = null;
        protected ResourceLocation resourceLocation = null;

        public TextureUsageInfo(boolean useResourceLocation, String texturePath) {
            this.useResourceLocation = useResourceLocation;
            this.texturePath = texturePath;
        }

        public TextureUsageInfo(String texturePath, boolean useResourceLocation) {
            this(useResourceLocation, texturePath);
        }

        public TextureUsageInfo(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
            useResourceLocation = true;
        }

        public String getTexturePath() {
            return texturePath;
        }

        public ResourceLocation getResourceLocation() {
            return resourceLocation;
        }

        public boolean doesUseResourceLocation() {
            return useResourceLocation && getResourceLocation() != null;
        }

    }

}
