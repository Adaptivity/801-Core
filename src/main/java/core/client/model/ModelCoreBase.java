package core.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Heavily based-off of my BluePower 'ModelBluePowerBase' class.
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public abstract class ModelCoreBase extends ModelBase {

    private Tessellator tessellator = null;

	/**
	 * Replacement for {@link #render(Entity, float, float, float, float, float, float)}.
	 */
	protected abstract void renderModel(Entity entity, Tessellator tessallator, float f, float f1, float f2, float f3, float f4, float f5);

	@Override
	public final void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (getTessellator() == null) {
            setTessellator(new Tessellator());
        }
        if (doesOverrideRenderer()) {
            overrideRenderer(getTessellator());
            return;
        }
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		renderModel(entity, getTessellator(), f, f1, f2, f3, f4, f5);
	}

    /**
     * Make sure to put your render code in here, if you're overriding the default renderer.
     */
    protected void overrideRenderer(Tessellator tessellator) {
    }

    protected boolean doesOverrideRenderer() {
        return false;
    }

    protected final Tessellator getTessellator() {
        return tessellator;
    }

    protected final void setTessellator(Tessellator tessellator) {
        this.tessellator = tessellator;
    }

}
