package core.asm;

import core.helpers.ListHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Master801 on 12/28/2014 at 12:48 PM.
 * @author Master801
 */
@Name("801-Core")
@MCVersion("1.7.10")
@TransformerExclusions({"core.asm", "core.asm.transformerbases", "core.asm.transformers"})
public final class CoreASM implements IFMLLoadingPlugin {

    private static final List<String> TRANSFORMER_CLASSES = new ArrayList<String>();
    private static File jarFile = null;

    @Override
    public String[] getASMTransformerClass() {
        String[] classes = new String[CoreASM.TRANSFORMER_CLASSES.size()];
        for(int i = 0; i < classes.length; i++) {
            String tc = CoreASM.TRANSFORMER_CLASSES.get(i);
            if (tc != null) {
                classes[i] = tc;
            }
        }
        return classes;
    }

    @Override
    public String getModContainerClass() {
        return "core.asm.CoreModContainer";
    }

    @Override
    public String getSetupClass() {
        return null;//I don't use a setup class, so this is not needed.
    }

    @Override
    public void injectData(Map<String, Object> data) {
        CoreASM.jarFile = (File)data.get("coremodLocation");
    }

    @Override
    public String getAccessTransformerClass() {
        return ReflectionHelper.getStringFromClass(CoreAccessTransformer.class);
    }

    public static void addTransformerClass(String classTransformer) {
        ListHelper.addObjectToListWhileChecking(CoreASM.TRANSFORMER_CLASSES, classTransformer);
    }

    public static File getCoreJarFile() {
        if (CoreASM.jarFile == null) {
            throw new NullPointerException("Core's jar file is null!");
        }
        return CoreASM.jarFile;
    }

    static {
        //TODO Add more transformers.
    }

}
