package core.asm.transformers;

import core.Core;
import core.api.plugin.Plugin;
import core.asm.transformerbases.ClassTransformerCoreBase;
import core.common.resources.CoreEnums;
import core.helpers.LoggerHelper;
import core.helpers.PluginHelper;
import core.helpers.ReflectionHelper;

import java.lang.reflect.Field;

/**
 * Created by Master801 on 1/18/2015 at 12:19 PM.
 * @author Master801
 */
public final class PluginTransformer extends ClassTransformerCoreBase {

    @Override
    protected byte[] transformClass(String classToTransform, byte[] classData) {
        Class<?> unknownClass = ReflectionHelper.getClassFromString(classToTransform);
        if (unknownClass != null) {
            if (unknownClass.isAnnotationPresent(Plugin.class)) {
                Plugin plugin = unknownClass.getAnnotation(Plugin.class);
                String name = plugin.name();
                Object instance = null;
                try {
                    instance = unknownClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (instance != null) {
                    PluginHelper.INSTANCE.injectInstance(unknownClass, instance);
                    for(Field field : ReflectionHelper.getFields(classToTransform)) {
                        if (field.isAnnotationPresent(Plugin.PluginInstance.class) && !ReflectionHelper.isModifierFinal(field)) {
                            ReflectionHelper.setFieldValue(field, instance, instance);//Sets the instance field to the instance we are using.
                            break;
                        } else if (ReflectionHelper.isModifierFinal(field)) {
                            LoggerHelper.addAdvancedMessageToLogger(Core.instance, CoreEnums.LoggerEnum.INFO, "The plugin's instance field's modifier is final! Plugin: %s", name);
                            break;
                        }
                    }
                }
            }
        }
        return classData;
    }

}
