package core.client.gui.screen;

import core.Core;
import core.api.client.texture.ITexture;
import core.client.gui.buttons.GuiButtonIcon;
import core.common.resources.CoreEnums;
import core.common.resources.CoreResources;
import core.helpers.*;
import core.plugins.PluginMenuOverhaul;
import cpw.mods.fml.client.GuiModList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.realms.RealmsBridge;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Feel-free to override this Class.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class GuiScreenMainMenu extends GuiScreen {

    public final List<GuiButton> buttonList = (List<GuiButton>)super.buttonList;

    @Override
    public void drawScreen(int x, int y, float f) {
        drawDefaultBackground();
        GLHelper.glColour4f();
        if (Boolean.valueOf(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Enable Background"))) {
            renderBackground();
        }
        if (Boolean.valueOf(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Enable Logo"))) {
            renderLogo();
        }
		super.drawScreen(x, y, f);
	}

	@Override
	public void initGui() {
        buttonList.clear();
        addButtons(width / 2, (height / 2) - 10);
	}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch(button.id) {
            case 0://Singleplayer
                CoreResources.getMinecraft().displayGuiScreen(new GuiSelectWorld(this));
                break;
            case 1://Multiplayer
                CoreResources.getMinecraft().displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2://Online (Realms)
                new RealmsBridge().switchToRealms(this);
                break;
            case 3://Mods
                CoreResources.getMinecraft().displayGuiScreen(new GuiModList(this));
                break;
            case 4://Languages
                CoreResources.getMinecraft().displayGuiScreen(new GuiLanguage(this, CoreResources.getGameOptions(), CoreResources.getMinecraft().getLanguageManager()));
                break;
            case 5://Options
                CoreResources.getMinecraft().displayGuiScreen(new GuiOptions(this, CoreResources.getGameOptions()));
                break;
            case 6://Quit
                CoreResources.getMinecraft().shutdown();
                break;
        }
    }

    protected void addButtons(int adjustedX, int adjustedY) {
        buttonList.add(new GuiButton(0, adjustedX + 110, adjustedY - 90, 90, 20, LanguageHelper.getLocalisedString("menu.singleplayer")));//Singleplayer
        buttonList.add(new GuiButton(1, adjustedX + 110, adjustedY - 60, 90, 20, LanguageHelper.getLocalisedString("menu.multiplayer")));//Multiplayer
        buttonList.add(new GuiButton(2, adjustedX + 110, adjustedY - 30, 90, 20, LanguageHelper.getLocalisedString("menu.online")));//Online (Realms)
        buttonList.add(new GuiButton(3, adjustedX + 110, adjustedY, 90, 20, LanguageHelper.getLocalisedString("menu.mods")));//Mods
        buttonList.add(new GuiButton(4, adjustedX + 110, adjustedY + 30, 90, 20, LanguageHelper.getLocalisedString("menu.languages")));//Languages
        buttonList.add(new GuiButton(5, adjustedX + 110, adjustedY + 60, 90, 20, LanguageHelper.getLocalisedString("menu.options")));//Options
        buttonList.add(new GuiButton(6, adjustedX + 110, adjustedY + 90, 90, 20, LanguageHelper.getLocalisedString("menu.quit")));//Quit
        buttonList.add(new GuiButtonIcon(7, adjustedX + 20, adjustedY + 100, 20, 20, true, new TextureHelper.TextureUsageInfo(false, "/resources/textures/gui/menu/Edit_Icon.png")));
    }

    protected void renderBackground() {
        if (Boolean.valueOf(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Enable Panorama"))) {
            renderPanorama();
            return;
        }
        ITexture backgroundTexture = TextureHelper.createNewCustomTexture(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Background Texture Path"));
        TextureHelper.bindTexture(backgroundTexture);
        //TODO Rewrite rendering method.
        TessellatorHelper.drawTextureToEntireScreen(backgroundTexture.getWidth(), backgroundTexture.getHeight());
    }

    /**
     * Only called when panoramas are enabled.
     */
    protected void renderPanorama() {
        ITexture panoramaTexture = TextureHelper.createNewCustomTexture(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Panorama Texture Path"));
        //TODO Witchcraft.
    }

    protected void renderLogo() {
        ITexture logoTexture = TextureHelper.createNewCustomTexture(PluginMenuOverhaul.MENU_OVERHAUL_CONFIG_FILE.getValueFromKey("Logo Texture Path"));
        TextureHelper.bindTexture(logoTexture);
    }

}
