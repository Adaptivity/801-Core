package core.client.renderer.item;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import core.common.integration.othermods.modelloader.glloader.GLModel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Only used for rendering models as items.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class ItemRendererModelCoreBase extends ItemRendererCoreBase {

	protected abstract GLModel getModel();

	/**
	 * Bind the texture in this method.
	 */
	protected abstract void bindTexture(ItemStack stack);

    protected ItemRendererModelCoreBase() {
    }

	@Override
	public void render(ItemStack stack, float xCoord, float yCoord, float zCoord, Entity entity, Tessellator tessellator) {
		bindTexture(stack);
		if (getModel() != null) {
			getModel().render();
		}
	}

}
