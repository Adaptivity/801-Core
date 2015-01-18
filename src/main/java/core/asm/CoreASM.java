package core.asm;

import core.asm.transformers.DeSRGTransformer;
import core.asm.transformers.InjectorTransformer;
import core.helpers.ListHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.Name;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;

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
@TransformerExclusions({"core.asm."})
public final class CoreASM implements IFMLLoadingPlugin {

    public static final int ASM_VERSION = Opcodes.ASM5;

    private static final List<String> TRANSFORMER_CLASSES = new ArrayList<String>();
    private static File jarFile = null;
    private static File minecraftDir = null;

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
        return ReflectionHelper.getStringFromClass(CoreSetup.class);
    }

    @Override
    public void injectData(Map<String, Object> data) {
        CoreASM.jarFile = (File)data.get("coremodLocation");
        CoreASM.minecraftDir = (File)data.get("mcLocation");
    }

    @Override
    public String getAccessTransformerClass() {
        return ReflectionHelper.getStringFromClass(CoreAccessTransformer.class);
    }

    public static void addTransformerClass(String classTransformer) {
        ListHelper.addObjectToListWhileChecking(CoreASM.TRANSFORMER_CLASSES, classTransformer);
    }

    public static void addTransformerClass(Class<?> classTransformer) {
        ListHelper.addObjectToListWhileChecking(CoreASM.TRANSFORMER_CLASSES, ReflectionHelper.getStringFromClass(classTransformer));
    }

    public static File getCoreJarFile() {
        return CoreASM.jarFile;
    }

    public static File getMinecraftDirectory() {
        return CoreASM.minecraftDir;
    }

    public static List<LabelNode> getLabelsInInstructions(InsnList instructions) {
        List<LabelNode> labels = new ArrayList<LabelNode>();
        for(AbstractInsnNode node : instructions.toArray()) {
            if (node instanceof LabelNode) {
                labels.add((LabelNode)node);
            }
        }
        return labels;
    }

    static {
        CoreASM.addTransformerClass(DeSRGTransformer.class);
        CoreASM.addTransformerClass(InjectorTransformer.class);
    }

}
