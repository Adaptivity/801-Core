package core.api.asm;

import core.asm.transformerbases.ClassTransformerCoreBase;

/**
 * Created by Master801 on 1/17/2015 at 1:44 PM.
 * @author Master801
 */
public interface IAsmInjector {

    Class<? extends ClassTransformerCoreBase> getTransformerForClass(String classToTransform, byte[] classData);

}
