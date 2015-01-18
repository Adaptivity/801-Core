package core.asm.transformerbases;

import core.api.asm.IAsmInjector;
import core.helpers.ReflectionHelper;
import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Created by Master801 on 12/29/2014 at 11:34 AM.
 * @author Master801
 */
public abstract class ClassTransformerCoreBase implements IClassTransformer {

    /**
     * @param classToTransform The class to transform. Ex: "core.Core"
     * @param classData The class' current byte-code (data).
     * @return The class' new byte-code (data).
     */
    protected abstract byte[] transformClass(String classToTransform, byte[] classData);

    @Override
    public final byte[] transform(String s, String s1, byte[] bytes) {
        s = s.replace('/', '.');
        if (IAsmInjector.class.isAssignableFrom(ReflectionHelper.getClassFromString(s))) {//Checks if the class is a hook for mods.
            IAsmInjector injector = null;
            try {
                injector = (IAsmInjector)ReflectionHelper.getClassFromString(s).newInstance();
            } catch(IllegalAccessException exception) {
                exception.printStackTrace();
            } catch(InstantiationException exception) {
                exception.printStackTrace();
            }
            if (injector != null) {
                ClassTransformerCoreBase transformer = null;
                try {
                    transformer = injector.getTransformerForClass(s, bytes).newInstance();
                } catch(IllegalAccessException exception) {
                    exception.printStackTrace();
                } catch(InstantiationException exception) {
                    exception.printStackTrace();
                }
                if (transformer != null) {
                    return transformer.transformClass(s, bytes);
                }
            }
            return bytes;
        }
        return transformClass(s, bytes);
    }

}
