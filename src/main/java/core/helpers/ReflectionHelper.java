package core.helpers;

import core.Core;
import core.exceptions.CoreExceptions.CoreNullPointerException;
import core.common.resources.CoreEnums.LoggerEnum;
import net.minecraftforge.common.util.EnumHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * To help with getting private fields/methods. If the field/method is static, it's fine. If not, you cannot get the field/method. Without a instance of the class of course.
 * Remember kids, Java Reflection causes a shit-ton of lag.
 * @author Master801
 */
public final class ReflectionHelper {

    public static Class<?> getClassFromString(String clazz) {
        try {
            return Class.forName(clazz.replace('/', '.'));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CoreNullPointerException("Failed to find the specified Class! Class: '%s'", clazz);
        }
    }

    public static String getStringFromClass(Class<?> clazz) {
        return clazz.getCanonicalName();
    }

	/**
	 * Gets a method from the specified class file name.
	 * @param parameters The Class's parameters.
	 */
	public static Method getMethod(String classFileName, String methodName, Class<?>... parameters) {
		if (classFileName != null && methodName != null && parameters != null) {
			try {
				Method method = ReflectionHelper.getClassFromString(classFileName).getDeclaredMethod(methodName, parameters);
				if (method == null) {
					throw new CoreNullPointerException("Could not find the method, with the method name '%s'!", methodName);
				}
				if (!method.isAccessible()) {
					method.setAccessible(true);
				}
				return method;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.ERROR, "Failed to get method from class file! Class: " + Class.forName(classFileName).getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameters) {
		return ReflectionHelper.getMethod(ReflectionHelper.getStringFromClass(clazz), methodName, parameters);
	}

	/**
	 * Gets a field from the specified class file name.
	 */
	public static Field getField(String classFileName, String fieldName) {
		Field field = null;
		if (classFileName == null) {
			throw new CoreNullPointerException("ClassFileName for 'getField' in ReflectionHelper, is null!");
		}
		if (fieldName == null) {
			throw new CoreNullPointerException("FieldName for 'getField' in ReflectionHelper, is null!");
		}
		try {
			field = ReflectionHelper.getClassFromString(classFileName).getDeclaredField(fieldName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (field != null) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			return field;
		}
		try {
			LoggerHelper.addMessageToLogger(Core.instance, LoggerEnum.ERROR, "Failed to get field from class file! Class: " + Class.forName(classFileName).getName() + " Field Name: " + fieldName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets all of the methods from the specified class file. If the class file already exists, just use (Class File Name).getName() for the classFileName parameter.
	 */
	public static Method[] getMethods(String classFileName) {
		if (classFileName != null) {
			try {
				return ReflectionHelper.getClassFromString(classFileName).getDeclaredMethods();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return null;
	}

	/**
	 * Gets all of the fields from the specified class file. If the class file already exists, just use (Class File Name).getName() for the classFileName parameter.
	 */
	public static Field[] getFields(String classFileName) {
		if (classFileName != null) {
			try {
				return ReflectionHelper.getClassFromString(classFileName).getDeclaredFields();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        return null;
	}

	/**
	 * Automatically sets the method to be accessible! No need for having to do wonky things!
	 * If you invoke a void method, it'll probably return null;
	 * @param instance The Class's instance in-case the method is not static.
	 * @param args If the Object is equal to new Object[0], it automatically uses only the instance when invoking.
	 */
	public static <T> T invokeMethod(Method method, Object instance, Object... args) {
		if (method == null) {
			throw new CoreNullPointerException("Method is null!");
		}
		try {
			T object = null;
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
			if (args != null) {
                object = (T) method.invoke(instance, args);
            } else {
                object = (T) method.invoke(instance);
            }
            return object;
		} catch(Exception e) {
			e.printStackTrace();
		}
		throw new CoreNullPointerException("Failed to invoke the method! Class: '%s', Method: '%s'.", instance != null ? (instance.getClass().toString().replace("class ", "")) : ("!!UNKNOWN!! " + method.getClass().toString()), method.getName());
	}

    public static void invokeVoidMethod(Method method, Object instance, Object... args) {
        if (method == null) {
            throw new CoreNullPointerException("Method is null!");
        }
        try {
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            if (args != null) {
                method.invoke(instance, args);
            } else {
                method.invoke(instance);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Sets the value of the field to something else.
	 */
	public static void setFieldValue(Field field, Object instance, Object value) {
		if (field == null) {
			throw new CoreNullPointerException("The field is null!");
		}
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
			if (ReflectionHelper.isModifierFinal(field)) {
				throw new CoreNullPointerException("The field has a final modifier! Field: '%s'", field.toString());
			}
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Used only to get the Field's value.
	 */
	public static <T> T getFieldValue(Field field, Object instance) {
		if (field == null) {
			throw new CoreNullPointerException("The Field is null!");
		}
		try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
			return (T)field.get(instance);
		} catch(Exception e) {
			e.printStackTrace();
		}
        throw new CoreNullPointerException("Failed to get the Field's value!");
	}

	public static Field getField(Class clazz, String fieldName) {
		return ReflectionHelper.getField(clazz.getCanonicalName(), fieldName);
	}

	public static <T> Constructor<T> getConstructor(Class<T> clazz, ClassParameters parameters) {
		Constructor<T> constructor = null;
		try {
			constructor = clazz.getDeclaredConstructor(parameters.getClassArray());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (constructor != null) {
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
			return constructor;
		}
		return null;
	}

	public static <T> T createNewObjectFromConstructor(Constructor<T> constructor, ObjectParameters parameters) {
		try {
			return constructor.newInstance(parameters.getObjectArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static <T> T createNewInstanceOfClass(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * @return True if the field is final.
     */
    public static boolean isModifierFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    /**
     * True if the method is final.
     */
    public static boolean isModifierFinal(Method method) {
        return Modifier.isFinal(method.getModifiers());
    }

    public static void setFinalFieldValue(Field finalField, Object fieldInstance, Object fieldValue) {
        if (!ReflectionHelper.isModifierFinal(finalField)) {
            return;
        }
        boolean previousAccessible = finalField.isAccessible();
        if (!finalField.isAccessible()) {
            finalField.setAccessible(true);
        }
        try {
            finalField.setInt(fieldInstance, finalField.getModifiers() & ~Modifier.FINAL);
            finalField.set(fieldInstance, fieldValue);
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        if (finalField.isAccessible() != previousAccessible) {
            finalField.setAccessible(previousAccessible);
        }
    }

	/**
	 * This is just a method that simply hooks into Forge's existing helper.
	 * <p>
	 *     Note, the only reason why I created this method instead of using Forge's existing "addEnum" method, is because it kept crashing, and this was the only method I could find that works.
	 * </p>
	 */
	public static <T extends Enum<T>> T addNewEnum(Class<T> enumClass, String enumName, ClassParameters classParameters, ObjectParameters objectParameters) {
		boolean isSetup = ReflectionHelper.getFieldValue(ReflectionHelper.getField(EnumHelper.class, "isSetup"), null);
		if (!isSetup) {
			ReflectionHelper.invokeMethod(ReflectionHelper.getMethod(EnumHelper.class, "setup"), null);
			ReflectionHelper.setFieldValue(ReflectionHelper.getField(EnumHelper.class, "isSetup"), null, true);
		}
		return EnumHelper.addEnum(enumClass, enumName, classParameters.getClassArray(), objectParameters.getObjectArray());
	}

    /**
	 * @author Master801
	 */
	public static final class ClassParameters {

		private Class<?>[] array;

		public ClassParameters(Class<?>... array) {
			this.array = array;
		}

		public Class<?>[] getClassArray() {
			return array;
		}

        public ClassParameters setClassArray(Class<?>[] newArray) {
            if (array != newArray) {
                array = newArray;
            }
            return this;
        }

	}

	/**
	 * @author Master801
	 */
	public static final class ObjectParameters {

        private Object[] array;

        public ObjectParameters() {
            array = new Object[0];
        }

        public ObjectParameters(Object... array) {
			this.array = array;
		}

        public final Object[] getObjectArray() {
			return array;
		}

        public ObjectParameters setObjectArray(Object[] objectParameters) {
            array = objectParameters;
            return this;
        }
    }

}
