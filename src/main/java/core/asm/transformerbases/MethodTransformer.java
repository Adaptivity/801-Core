package core.asm.transformerbases;

import core.helpers.StringHelper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;

/**
 * Created by Master801 on 12/30/2014 at 10:05 AM.
 * <p>
 *     Only supports one method to transform.
 * </p>
 * @author Master801
 */
public abstract class MethodTransformer extends MethodsTransformer {

    protected abstract void transformMethod(ClassNode classNode, MethodNode methodNode, int methodNodeIndex, boolean isObfuscated);

    protected abstract MethodNames getMethodName();

    @Override
    protected final void transformMethods(int methodID, ClassNode classNode, MethodNode methodNode, int methodNodeIndex, boolean isObfuscated) {
        if (methodID > 0) {
            final String error = StringHelper.advancedMessage("MethodID (%s) for class MethodTransformer is bigger than zero?\nDid you try to manually a method? Use MethodsTransformer instead if you were trying to do so!", methodID);
            throw new IndexOutOfBoundsException(error);
        }
        transformMethod(classNode, methodNode, methodNodeIndex, isObfuscated);
    }

    @Override
    protected final List<MethodNames> getMethodNames(List<MethodNames> existingNames) {
        existingNames.add(getMethodName());
        return existingNames;
    }

    @Override
    protected final MethodNames[] getMethodNamesArray() {
        return super.getMethodNamesArray();
    }

}
