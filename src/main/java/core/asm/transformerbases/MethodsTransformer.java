package core.asm.transformerbases;

import com.google.common.base.Joiner;
import core.Core;
import core.common.resources.CoreEnums.LoggerEnum;
import core.exceptions.CoreExceptions;
import core.helpers.LoggerHelper;
import core.helpers.StringHelper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Master801 on 12/30/2014 at 1:09 PM.
 * <p>
 *     Only supports multiple methods to transform.
 * </p>
 * @author Master801
 */
public abstract class MethodsTransformer extends ClassTransformerCoreBase {

    /**
     * Calls a new instance of a list for you, instead of you doing it yourself.
     */
    protected abstract List<MethodNames> getMethodNames(List<MethodNames> newList);

    protected abstract void transformMethods(int methodID, ClassNode classNode, MethodNode methodNode, int methodNodeIndex, boolean isObfuscated);

    /**
     * @return The path and name of the class you're transforming.
     * Ex: 'core.Core'
     */
    protected abstract ClassNames getTransformingClassName();

    /**
     * @return The 'description' of the method.
     * Ex: '()V'
     */
    protected abstract MethodSignature getMethodSignature(MethodNames methodNames);

    @Override
    protected final byte[] transformClass(String transformingClassName1, String transformingClassName2, byte[] classData) {
        String className;
        if (transformingClassName1.equals(className = getTransformingClassName().getDeobfuscatedName())) {
            return privatelyTransformMethods(className, classData, false);
        } else if (transformingClassName1.equals(className = getTransformingClassName().getObfuscatedName())) {
            return privatelyTransformMethods(className, classData, true);
        }
        return classData;
    }

    private byte[] privatelyTransformMethods(String transformingClassName1, byte[] classData, boolean isClassObfuscated) {
        if (isClassObfuscated) {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Attempting to patch class %s/%s...", transformingClassName1, getTransformingClassName().getDeobfuscatedName());
        } else {
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Attempting to patch class %s/%s...", transformingClassName1, getTransformingClassName().getObfuscatedName());
        }
        ClassReader classReader = new ClassReader(classData);
        ClassNode classNode = new ClassNode();
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        classReader.accept(classNode, 0);
        classNode.accept(classWriter);
        int methodID = -1;
        int methodIndex = -1;
        String methodName = null;
        for (int i = 0; i < classNode.methods.size(); i++) {
            List<MethodNames> privateNames = getMethodNames(new ArrayList<MethodNames>());
            if (privateNames == null) {
                privateNames = convertArrayToList();
            }
            for (MethodNames methodNames : privateNames) {
                methodID = methodNames.getMethodID();
                MethodNode methodNode = classNode.methods.get(i);
                for (String name : methodNames.getNames()) {
                    if (methodNode == null) {
                        if (isClassObfuscated) {
                            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The method node is null! Class: %s/%s, Method index: %d", transformingClassName1, getTransformingClassName().getDeobfuscatedName(), i);
                        } else {
                            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "The method node is null! Class: %s/%s, Method index: %d", transformingClassName1, getTransformingClassName().getObfuscatedName(), i);
                        }
                        LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.WARN, "Attempting to find another node...");
                        continue;
                    }
                    String signature = getMethodSignature(methodNames).getDeobfuscatedSignature();
                    if (signature == null) {
                        signature = getMethodSignature(methodNames).getObfuscatedSignature();
                    }
                    if (signature == null) {
                        throw new CoreExceptions.CoreNullPointerException("ASM handler caught a method signature error! Class: '%s', Method: '%s'", transformingClassName1, methodName);
                    }
                    if (methodNode.name.equals(name) && methodNode.desc.equals(signature)) {
                        methodIndex = i;
                        methodName = methodNode.name;
                        break;//Stop looking for the method since it's been found.
                    }
                }
            }
        }
        if (methodID != -1 || methodIndex != -1 || methodName != null) {
            transformMethods(methodID, classNode, classNode.methods.get(methodIndex), methodIndex, isClassObfuscated);
            List<MethodNames> list = getMethodNames(new ArrayList<MethodNames>());
            if (list == null) {
                list = convertArrayToList();
            }
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, writePatchMessage(getTransformingClassName(), list, false, isClassObfuscated));
            return classWriter.toByteArray();
        } else {
            List<MethodNames> list = getMethodNames(new ArrayList<MethodNames>());
            if (list == null) {
                list = convertArrayToList();
            }
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, writePatchMessage(getTransformingClassName(), list, true, isClassObfuscated));
            return classData;
        }
    }

    protected String writeMethodNamesIntoString(List<MethodNames> methodNamesList) {
        List<String> compiledStringList = new ArrayList<String>();
        for(MethodNames methodNames : methodNamesList) {
            String[] names = methodNames.getNames();
            String compiledString = Joiner.on('/').join(names);
            compiledStringList.add(compiledString);
        }
        return Joiner.on("and ").join(compiledStringList);
    }

    protected String writePatchMessage(ClassNames className, List<MethodNames> methodNamesList, boolean hasFailed, boolean isObfuscated) {
        String obfuscated = className.getObfuscatedName(), deobfuscated = className.getDeobfuscatedName();
        if (isObfuscated) {
            obfuscated = className.getDeobfuscatedName();
            deobfuscated = className.getObfuscatedName();
        }
        if (hasFailed) {
            return StringHelper.advancedMessage("Failed to patch class %s/%s and methods[s] %s!", deobfuscated, obfuscated, writeMethodNamesIntoString(methodNamesList));
        }
        return StringHelper.advancedMessage("Successfully patched class %s/%s and method[s] %s!", deobfuscated, obfuscated, writeMethodNamesIntoString(methodNamesList));
    }

    protected void visitMethod(MethodNode node, int opCode, ClassNames classNames, MethodNames methodNames, MethodSignature methodSignatures, boolean useSRG, boolean isObfuscated) {
        if (isObfuscated) {
            if (useSRG) {
                node.visitMethodInsn(opCode, classNames.getObfuscatedName(), methodNames.getSeargeName(), methodSignatures.getObfuscatedSignature(), false);
                return;
            }
            node.visitMethodInsn(opCode, classNames.getObfuscatedName(), methodNames.getObfuscatedName(), methodSignatures.getObfuscatedSignature(), false);
            return;
        }
        if (useSRG) {
            node.visitMethodInsn(opCode, classNames.getDeobfuscatedName(), methodNames.getSeargeName(), methodSignatures.getDeobfuscatedSignature(), false);
            return;
        }
        node.visitMethodInsn(opCode, classNames.getDeobfuscatedName(), methodNames.getDeobfuscatedName(), methodSignatures.getDeobfuscatedSignature(), false);
    }

    protected MethodNames[] getMethodNamesArray() {
        return null;
    }

    protected final List<MethodNames> convertArrayToList() {
        if (getMethodNamesArray() == null) {
            return null;
        }
        List<MethodNames> fakeList = Arrays.asList(getMethodNamesArray());
        if (!fakeList.isEmpty()) {
            return fakeList;
        }
        return null;
    }

    protected static final class ClassNames {

        private final String deobfuscatedName, obfuscatedName;

        public ClassNames(String className) {
            this(className, className);
        }

        public ClassNames(String deobfuscatedName, String obfuscatedName) {
            this.deobfuscatedName = deobfuscatedName;
            this.obfuscatedName = obfuscatedName;
        }

        public String getDeobfuscatedName() {
            return deobfuscatedName;
        }

        public String getObfuscatedName() {
            return obfuscatedName;
        }

        public String[] getNames() {
            return new String[] { getDeobfuscatedName(), getObfuscatedName() };
        }

    }

    protected static final class MethodNames {

        private final int methodID;
        private final String deobfuscatedName, seargeName, obfuscatedName;

        public MethodNames(String methodName) {
            this(-1, methodName);
        }

        public MethodNames(int methodID, String methodName) {
            this(methodID, methodName, methodName, methodName);
        }

        public MethodNames(int methodID, String deobfuscatedName, String seargeName, String obfuscatedName) {
            this.methodID = methodID;
            this.deobfuscatedName = deobfuscatedName;
            this.seargeName = seargeName;
            this.obfuscatedName = obfuscatedName;
        }

        public int getMethodID() {
            return methodID;
        }

        public String getDeobfuscatedName() {
            return deobfuscatedName;
        }

        public String getSeargeName() {
            return seargeName;
        }

        public String getObfuscatedName() {
            return obfuscatedName;
        }

        public String[] getNames() {
            return new String[] { getDeobfuscatedName(), getSeargeName(), getObfuscatedName() };
        }

    }

    protected static final class MethodSignature {

        private final String deobfuscatedSignature, obfuscatedSignature;

        public MethodSignature(String signature) {
            this(signature, signature);
        }

        public MethodSignature(String deobfuscatedSignature, String obfuscatedSignature) {
            this.deobfuscatedSignature = deobfuscatedSignature;
            this.obfuscatedSignature = obfuscatedSignature;
        }

        public String getDeobfuscatedSignature() {
            return deobfuscatedSignature;
        }

        public String getObfuscatedSignature() {
            return obfuscatedSignature;
        }

    }

}
