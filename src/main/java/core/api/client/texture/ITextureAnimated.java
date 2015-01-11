package core.api.client.texture;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Created by Master801 on 10/13/2014.
 * @author Master801
 */
public interface ITextureAnimated extends ITexture {

    boolean doesUseFrameTime();

    int getFrameTime();

    /**
     * Input the frame number to get the frame's gl id.
     */
    Map<Integer, BufferedImage> getFrameID();

}
