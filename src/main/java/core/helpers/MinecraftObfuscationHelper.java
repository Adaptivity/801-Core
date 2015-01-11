package core.helpers;

import core.common.resources.CoreResources;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper.UnableToFindFieldException;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * I don't claim ownership on half of this code.
 * It's mainly just based off of FML's code.
 * @author Master801
 */
public final class MinecraftObfuscationHelper {

	public static final class UnableToAccessMethodException extends RuntimeException {

		private final String[] methodNameList;

		public UnableToAccessMethodException(String[] methodNames, Exception e) {
			super(e);
			methodNameList = methodNames;
		}

	}

	MinecraftObfuscationHelper() {
	}

	public static String[] remapMethodNames(String className, String... methodNames) {
		String internalClassName = MinecraftObfuscationHelper.remapClass(false, className);
		String[] mappedNames = new String[methodNames.length];
		int i = 0;
		for (String mName : methodNames) {
			mappedNames[i++] = CoreResources.getRemapper().mapMethodName(internalClassName, mName, null);
		}
		return mappedNames;
	}

	private static Method findMethod(Class<?> clazz, ClassArray classArray, String... methodNames) {
		Exception failed = null;
		for (String methodName : methodNames) {
			try {
				Method m = null;
				if (classArray.getArray() == new Class[0]) {
					m = clazz.getDeclaredMethod(methodName);
				} else {
					m = clazz.getDeclaredMethod(methodName, classArray.getArray());
				}
				m.setAccessible(true);
				return m;
			}
			catch (Exception e) {
				failed = e;
			}
		}
		throw new UnableToFindFieldException(methodNames, failed);
	}

	private static <T, E> T getPrivateValueAndFindMethod(Class <? super E > classToAccess, E instance, ClassArray classArray, ObjectArray objectArray, String... fieldNames) {
		try {
			if (objectArray.getObjects() == new Object[0]) {
				return (T) findMethod(classToAccess, classArray, fieldNames).invoke(instance);
			} else {
				return (T) findMethod(classToAccess, classArray, fieldNames).invoke(instance, objectArray.getObjects());
			}
		} catch (Exception e) {
			throw new UnableToAccessMethodException(fieldNames, e);
		}
	}

	static <T, E> T getPrivateValue(Class<? super E> clazz, E instance, ClassArray classArray, ObjectArray objectArray, String... names) {
		try {
			return getPrivateValueAndFindMethod(clazz, instance, classArray, objectArray, MinecraftObfuscationHelper.remapMethodNames(clazz.getName(), names));
		} catch (UnableToFindFieldException e) {
			FMLLog.log(Level.ERROR,e,"Unable to locate any method %s on type %s", Arrays.toString(names), clazz.getName());
			throw e;
		} catch (UnableToAccessMethodException e) {
			FMLLog.log(Level.ERROR, e, "Unable to access any method %s on type %s", Arrays.toString(names), clazz.getName());
			throw e;
		}
	}

	public static ClassArray createNewParameterArray(Class... parameters) {
		return new ClassArray(parameters);
	}

	public static ObjectArray createNewObjectArray(Object... objects) {
		return new ObjectArray(objects);
	}

	/**
	 * To make it less confusing.
	 * This is only used for getting field values with the instance of the Class.
	 * (Non-static fields.)
	 */
	public static <T, E> T getMinecraftFieldValue(Class<? super E> clazz, E instance, String srgName, String deobName) {
		return ObfuscationReflectionHelper.getPrivateValue(clazz, instance, srgName, deobName);
	}

	/**
	 * To make it less confusing.
	 * This is only used for getting field values without the instance of the Class.
	 * (Static fields.)
	 */
	public static <T, E> T getMinecraftFieldValue(Class<? super E> clazz, String srgName, String deobName) {
		return ObfuscationReflectionHelper.getPrivateValue(clazz, null, srgName, deobName);
	}

	public static <T, E> void setMinecraftFieldValue(Class<? super E> clazz, E instance, String srgName, String deobName, Object value) {
		ObfuscationReflectionHelper.setPrivateValue(clazz, instance, value, srgName, deobName);
	}

	public static <T, E> void setMinecraftFieldValue(Class<? super E> clazz, String srgName, String deobName, Object value) {
		ObfuscationReflectionHelper.setPrivateValue(clazz, null, value, srgName, deobName);
	}

	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, E instance, String srgName, String deobName, ClassArray classArray, Object... parameters) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, instance, classArray, MinecraftObfuscationHelper.createNewObjectArray(parameters), srgName, deobName);
	}

	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, String srgName, String deobName, ClassArray classArray, Object... parameters) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, null, classArray, MinecraftObfuscationHelper.createNewObjectArray(parameters), srgName, deobName);
	}

	/**
	 * Only used for void methods.
	 */
	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, E instance, String srgName, String deobName, ClassArray classArray) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, instance, classArray, MinecraftObfuscationHelper.createNewObjectArray(), srgName, deobName);
	}

	/**
	 * Only used for static, and void methods.
	 */
	public static <T, E> T getMinecraftMethodValue(Class<? super E> clazz, String srgName, String deobName, ClassArray classArray) {
		return MinecraftObfuscationHelper.getPrivateValue(clazz, null, classArray, MinecraftObfuscationHelper.createNewObjectArray(), srgName, deobName);
	}

	public static <T, E> void invokeMinecraftMethod(Class<? super E> clazz, E instance, String srgName, String deobName, ClassArray classArray, Object... values) {
		MinecraftObfuscationHelper.getPrivateValue(clazz, instance, classArray, MinecraftObfuscationHelper.createNewObjectArray(values), srgName, deobName);
	}

	public static <T, E> void invokeMinecraftMethod(Class<? super E> clazz, String srgName, String deobName, ClassArray classArray, Object... values) {
		MinecraftObfuscationHelper.getPrivateValue(clazz, null, classArray, MinecraftObfuscationHelper.createNewObjectArray(values), srgName, deobName);
	}

    public static String remapClass(boolean remapToObfuscated, String classString) {
        classString = classString.replace('.', '/');
        if (remapToObfuscated) {
            return CoreResources.getRemapper().map(classString);
        }
        return CoreResources.getRemapper().unmap(classString);
    }

	public static final class ClassArray {

		private final Class[] parameters;

        private ClassArray() {
            parameters = new Class[0];
        }

		private ClassArray(Class... parameters) {
			this.parameters = parameters;
		}

		public final Class[] getArray() {
			return parameters;
		}

	}

	private static final class ObjectArray {

		private final Object[] objects;

        private ObjectArray() {
            objects = new Object[0];
        }

		private ObjectArray(Object... objects) {
			this.objects = objects;
		}

		public final Object[] getObjects() {
			return objects;
		}

	}


}
