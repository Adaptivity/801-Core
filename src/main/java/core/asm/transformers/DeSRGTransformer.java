package core.asm.transformers;

import core.Core;
import core.asm.CoreASM;
import core.asm.transformerbases.ClassTransformerCoreBase;
import core.common.resources.CoreEnums.LoggerEnum;
import core.helpers.LoggerHelper;
import core.helpers.MinecraftObfuscationHelper;
import core.helpers.ReflectionHelper;
import cpw.mods.fml.relauncher.CoreModManager;
import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Master801 on 1/12/2015 at 5:21 PM.
 * <p>
 *     DeObfuscates SRG names to MCP names.
 *     Should help me develop mods without deobfuscating them (of course if they've been obfuscated with srg names...). :D
 * </p>
 * @author Master801
 */
public final class DeSRGTransformer extends ClassTransformerCoreBase {

    public static final boolean IS_DEVELOPMENT_ENVIRONMENT = ReflectionHelper.getFieldValue(ReflectionHelper.getField(CoreModManager.class, "deobfuscatedEnvironment"), null);

    /**
     * These classes do not have srg names, so ignore them while checking.
     */
    private static final List<String> EXACT_BLACKLISTED_CLASSES = new ArrayList<String>();

    @Override
    protected byte[] transformClass(String classToTransform, byte[] classData) {
        if (!DeSRGTransformer.IS_DEVELOPMENT_ENVIRONMENT) {//Checks if we're in the developers' environment, to prevent users getting an error.
            LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "This is NOT a development environment, not transforming methods or fields in DeSRGTransformer...");
            return classData;
        }
        if (DeSRGTransformer.doesPackageEqual(classToTransform)) {
            return classData;
        } else if (classToTransform.startsWith("core.") || classToTransform.startsWith("transformerconvertors2.")) {//We already know that this stuff is deobfuscated, so no need to transform the class from srg to mcp.
            return classData;
        }
        Iterator<String> blacklistedClasses = EXACT_BLACKLISTED_CLASSES.iterator();
        do {
            if (blacklistedClasses.next().equals(classToTransform)) {
                return classData;
            }
        } while(blacklistedClasses.hasNext());
        if (ReflectionHelper.getClassFromString(classToTransform) == null) {//Checking if the class exists. Return no class data if it is not found.
            return null;
        }
        LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Attempting to transform class %s...", classToTransform.replace('.', '/'));

        ClassReader classReader = new ClassReader(classData);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        SRGRenamer srgRenamer = new SRGRenamer(classWriter, classToTransform);

        classReader.accept(srgRenamer, ClassReader.EXPAND_FRAMES);
        if (!srgRenamer.hasVisitedSomething) {
            return classData;
        }
        return classWriter.toByteArray();
    }

    public static boolean doesPackageEqual(String classPackage) {
        return classPackage.startsWith("net.minecraft.") || classPackage.startsWith("com.google.") || classPackage.startsWith("cpw.mods.fml.") || classPackage.startsWith("net.minecraftforge.") || classPackage.startsWith("joptsimple.") || classPackage.startsWith("com.mojang.") || classPackage.startsWith("io.netty.") || classPackage.startsWith("org.objectweb.") || classPackage.startsWith("java.") || classPackage.startsWith("tv.twitch.") || classPackage.startsWith("gnu.trove.") || classPackage.startsWith("org.slf4j.") || classPackage.startsWith("javassist.");
    }

    private class SRGRenamer extends ClassVisitor {

        final String className;

        boolean hasVisitedSomething = false;

        public SRGRenamer(ClassVisitor classVisitor, String className) {
            super(CoreASM.ASM_VERSION, classVisitor);
            this.className = className;
        }

        @Override
        public MethodVisitor visitMethod(int opCode, String method, String description, String signature, String[] exceptions) {
            if (method.startsWith("func_")) {
                String mcp = MinecraftObfuscationHelper.getMCPFromSRGM(method);
                if (!method.equals(mcp)) {
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Transforming method %s to %s, from class %s.", method, mcp, className);
                }
                method = mcp;
                hasVisitedSomething = true;
            }
            return cv.visitMethod(opCode, method, description, signature, exceptions);
        }

        @Override
        public FieldVisitor visitField(int opCode, String field, String description, String signature, Object value) {
            if (field.startsWith("field_")) {
                String mcp = MinecraftObfuscationHelper.getMCPFromSRGF(field);
                if (!field.equalsIgnoreCase(mcp)) {
                    LoggerHelper.addAdvancedMessageToLogger(Core.instance, LoggerEnum.INFO, "Transforming field %s to %s, from class %s.", field, mcp, className);
                }
                field = mcp;
                hasVisitedSomething = true;
            }
            return cv.visitField(opCode, field, description, signature, value);
        }

    }

    static {
        EXACT_BLACKLISTED_CLASSES.add("Start");
        EXACT_BLACKLISTED_CLASSES.add("Config");
        /*
        EXACT_BLACKLISTED_CLASSES.add("cofh.mod.BaseMod");
        EXACT_BLACKLISTED_CLASSES.add("cofh.mod.updater.IUpdatableMod");
        EXACT_BLACKLISTED_CLASSES.add("cofh.CoFHCore");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.command.ISubCommand");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.util.ConfigHandler");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.gui.GuiHandler");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.network.PacketBase");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.network.PacketCoFHBase");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.util.IBakeable");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.Proxy");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.ProxyClient");
        EXACT_BLACKLISTED_CLASSES.add("cofh.core.key.IKeyBinding");
        */
    }

}
