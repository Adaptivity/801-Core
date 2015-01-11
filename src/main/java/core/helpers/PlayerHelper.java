package core.helpers;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import core.common.resources.CoreResources;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public final class PlayerHelper {

	/**
	 * Much easier to just use this.
	 */
	public static void addChatMessage(World world, EntityPlayer player, String message) {
        if (WorldHelper.isServer(world)) {
            player.addChatMessage(new ChatComponentText(LanguageHelper.getLocalisedString(message)));
        }
    }

    public static void addAdvancedChatMessage(World world, EntityPlayer player, String message, Object... advanced) {
        PlayerHelper.addChatMessage(world, player, StringHelper.advancedMessage(message, advanced));
    }

	@SideOnly(Side.CLIENT)
	public static void renderSkins(AbstractClientPlayer playerClient) {
		String skinURL = null;
		if (CalenderHelper.isHalloween()) {
			//Credits to this guy for the skin!
			//http://toomuchminecraft.com/skins/jack-skellington.725/
			skinURL = "http://toomuchminecraft.com/attachments/jackskellington-png.2021/?version=665";
		}
        if (CalenderHelper.isChristmas()) {
            skinURL = "http://www.minecraftskins.com/downloadnew.php?file=newuploaded_skins/skin_20131124130313141379.png";
        }
        if (CalenderHelper.isNotchsBirthday()) {
			skinURL = "http://s3.amazonaws.com/MinecraftSkins/Notch.png";
		}
		if (CalenderHelper.isMaster801sBirthday()) {
			skinURL = "http://s3.amazonaws.com/MinecraftSkins/master801.png";
		}
		if (skinURL != null) {
            if (Boolean.valueOf(CoreResources.CORE_CONFIG_FILE.getValueFromKey("Enable Changing Skins"))) {
                playerClient.func_152121_a(Type.SKIN, CoreResources.getTextureManager().getDynamicTextureLocation("skins/" + playerClient.getGameProfile().getName(), new DynamicTexture(ImageHelper.getImageFromStream(InputStreamHelper.getStreamFromURL(skinURL)))));
            }
		}
	}

	public static void addAchievementToPlayer(EntityPlayer player, Achievement achievement) {
		player.addStat(achievement, 1);
	}

	public static boolean isPlayerSneaking(EntityPlayer player) {
		return player.isSneaking();
	}

	/**
	 * This is the opposite of {@link #isPlayerSneaking(EntityPlayer)}.
	 */
	public static boolean isPlayerNotSneaking(EntityPlayer player) {
		return !PlayerHelper.isPlayerSneaking(player);
	}

}
