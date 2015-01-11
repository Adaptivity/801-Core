package core.api.client.renderer.tileentity;

/**
 * Created by Master801 on 10/6/2014.
 * @author Master801
 */
public interface IRenderBounds {

    /**
     * This uses the Block's getCollisionBoundingBoxFromPool method.
     */
    boolean shouldUseBlockBoundsAsRenderBounds();

}
