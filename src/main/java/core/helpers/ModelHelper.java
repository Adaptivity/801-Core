package core.helpers;

import core.common.integration.othermods.modelloader.glloader.GL_OBJ_Reader;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.InputStream;

@SideOnly(Side.CLIENT)
public final class ModelHelper {

    /**
     * Going to eventually make a new method for loading wave front models.
     */
    @Deprecated
    public static GL_OBJ_Reader createNewWaveFrontModel(String modelPath) {
        if (modelPath == null) {
            throw new CoreNullPointerException("The model's path is null!");
        }
        InputStream stream = InputStreamHelper.getStreamFromResource(modelPath, false);
        if (stream == null) {
            throw new CoreNullPointerException("The model's input stream is null! Is the file missing?");
        }
        return new GL_OBJ_Reader(stream);
    }

}
