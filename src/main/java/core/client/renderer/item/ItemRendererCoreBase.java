package core.client.renderer.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Based-heavily off of my BluePower 'ItemRendererBluePowerBase' class file.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class ItemRendererCoreBase implements IItemRenderer {

	public abstract void render(ItemStack item, float xCoord, float yCoord, float zCoord, Entity entity, Tessellator tessellator);

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        Tessellator tessellator = new Tessellator();
        switch(type) {
		case ENTITY:
			render(item, -0.5F, 0F, -0.5F, (Entity)data[1], tessellator);
			break;
		default:
            render(item, 0, 0, 0, null, tessellator);
			break;
		}
        GL11.glPopMatrix();
	}

}
