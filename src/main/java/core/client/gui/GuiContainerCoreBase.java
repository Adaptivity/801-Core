package core.client.gui;

import core.api.client.gui.component.IGuiComponent;
import core.api.client.texture.ITexture;
import core.common.inventory.container.ContainerCoreBase;
import core.common.resources.CoreResources;
import core.helpers.*;
import core.helpers.TextureHelper.TextureUsageInfo;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master801 on 11/15/2014.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class GuiContainerCoreBase<T extends Container> extends GuiContainer {

    protected abstract TextureUsageInfo getTextureInfo();

    private final List<IGuiComponent> componentList = new ArrayList<IGuiComponent>();

    public GuiContainerCoreBase(T container) {
        super(container);
        changeGuiSize();
    }

    protected GuiContainerCoreBase(T container, int xSize, int ySize) {
        this(container);
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public final T getContainer() {
        return (T)inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();
        addButtons(getAdjustedWidth(), getAdjustedHeight(), getButtonList());
        addComponents(getAdjustedWidth(), getAdjustedHeight(), componentList);
    }

    protected final List<GuiButton> getButtonList() {
        return (List<GuiButton>)buttonList;
    }

    @Override
    protected final void drawGuiContainerBackgroundLayer(float par1, int x, int y) {
        GLHelper.glColour4f();
        int textureWidth = 0;
        int textureHeight = 0;
        if (getTextureInfo() != null) {
            if (getTextureInfo().doesUseResourceLocation()) {
                ResourceLocation location = new ResourceLocation(getTextureInfo().getTexturePath());
                CoreResources.getTextureManager().bindTexture(location);
                BufferedImage image = TextureHelper.getImageFromStream(ResourcePackHelper.getInputStreamFromCurrentPack(location.toString()));
                if (image != null) {
                    textureWidth = image.getWidth();
                    textureHeight = image.getHeight();
                }
            } else {
                ITexture texture = TextureHelper.createNewCustomTexture(getTextureInfo().getTexturePath());
                textureWidth = texture.getWidth();
                textureHeight = texture.getHeight();
                TextureHelper.bindTexture(texture);
            }
        } else {
            return;
        }
        if (textureWidth > 256 && textureHeight > 256) {
            TessellatorHelper.drawTexturedModalRectangleFromSizes(this, textureWidth, textureHeight, getAdjustedWidth(), getAdjustedHeight(), 0, 0, xSize, ySize);
        } else {
            drawTexturedModalRect(getAdjustedWidth(), getAdjustedHeight(), 0, 0, xSize, ySize);
        }
        drawBackgroundLayer(getAdjustedWidth(), getAdjustedHeight());
        for(IGuiComponent component : componentList) {
            component.onBackgroundLayerDrawn(getAdjustedWidth(), getAdjustedHeight());
        }
    }

    protected void drawBackgroundLayer(int adjustedX, int adjustedY) {
    }

    @Override
    protected final void drawGuiContainerForegroundLayer(int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawForegroundLayer(getAdjustedWidth(), getAdjustedHeight());
        for(IGuiComponent component : componentList) {
            component.onForeLayerDrawn(x, y);
        }
    }

    protected String getUnlocalisedGuiInventoryName() {
        if (getContainer() instanceof ContainerCoreBase) {
            return ((ContainerCoreBase)getContainer()).getIInventory().getInventoryName();
        }
        return null;
    }

    protected void drawForegroundLayer(int adjustedX, int adjustedY) {
        fontRendererObj.drawString(LanguageHelper.getLocalisedString("container.inventory"), 8, ySize - 96 + 2, 4210752);
        String localisedString = LanguageHelper.getLocalisedString(getUnlocalisedGuiInventoryName());
        fontRendererObj.drawString(localisedString, (xSize / 2) - (fontRendererObj.getStringWidth(localisedString) / 2), 6, 4210752);
    }

    protected final void addComponentToGuiContainer(IGuiComponent component) {
        if (!componentList.contains(component)) {
            componentList.add(component);
        }
    }

    protected void addButtons(int adjustedX, int adjustedY, List<GuiButton> buttonList) {
    }

    protected void addComponents(int adjustedX, int adjustedY, List<IGuiComponent> componentList) {
    }

    protected void changeGuiSize() {
    }

    public final int getAdjustedWidth() {
        return (width - xSize) / 2;
    }

    public final int getAdjustedHeight() {
        return (height - ySize) / 2;
    }

}
