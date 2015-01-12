package core.asm.transformerbases;

import net.minecraft.launchwrapper.IClassTransformer;

/**
 * Created by Master801 on 12/29/2014 at 11:34 AM.
 * @author Master801
 */
public abstract class ClassTransformerCoreBase implements IClassTransformer {

    protected abstract byte[] transformClass(String classToTransform, byte[] classData);

    @Override
    public final byte[] transform(String s, String s1, byte[] bytes) {
        return transformClass(s, bytes);
    }

}
