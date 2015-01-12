package core.helpers;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Master801 on 11/11/2014.
 * @author Master801
 */
public final class RandomHelper {

    public static Double convertFloatToDouble(float float_n) {
        return new Double(new Float(float_n).doubleValue());
    }

    public static Float convertDoubleToFloat(double double_n) {
        return new Float(new Double(double_n).floatValue());
    }

    public static Integer convertFloatToInteger(float float_n) {
//        return new Float(float_n).intValue();//FIXME?
        return new Integer(Math.round(float_n));
    }

    public static Float convertIntegerToFloat(int integer_n) {
        return new Float(new Integer(integer_n).floatValue());
    }

    public static Integer convertDoubleToInteger(double double_n) {
        return new Integer(new Double(double_n).intValue());
    }

    public static Short convertIntegerToShort(int integer_n) {
        return new Short(new Integer(integer_n).shortValue());
    }

    public static Integer convertShortToInteger(short short_n) {
        return new Integer(new Short(short_n).intValue());
    }

    public static Byte convertIntegerToByte(int integer_n) {
        return new Byte(new Integer(integer_n).byteValue());
    }

    /**
     * Duplicates an object into a array.
     * @param object The object you want to duplicate into a array.
     * @param amount The amount you want the object to duplicate.
     */
    public static <T> T[] duplicateObjectToArray(T object, int amount) {
        return RandomHelper.duplicateObjectToArrayWithExtra(object, amount, 0);
    }

    public static <T> T[] duplicateObjectToArrayWithExtra(T object, int amount, int extra) {
        T[] objectArray = (T[])new Object[amount + extra];//Cannot directly make a new instance from T.
        for(int i = 0; i < amount; i++) {
            objectArray[i] = object;
        }
        return objectArray;
    }

    public static Double convertIntegerToDouble(int integer_n) {
        return new Double(new Integer(integer_n).doubleValue());
    }

    public static Float convertStringToFloat(String string_s) {
        return Float.valueOf(string_s);
    }

    public static Float[] convertStringArrayToFloatArray(String[] string_s) {
        Float[] floats = new Float[string_s.length];
        for(int i = 0; i < string_s.length; i++) {
            floats[i] = Float.valueOf(string_s[i]);
        }
        return floats;
    }

    public static float[] convertFloatArrayToFloatArray(Float[] float_a_n) {
        float[] floats = new float[float_a_n.length];
        for(int i = 0; i < float_a_n.length; i++) {
            floats[i] = float_a_n[i].floatValue();
        }
        return floats;
    }

    public static Float[] convertFloatArrayToFloatArray(float[] float_a_n) {
        Float[] floats = new Float[float_a_n.length];
        for(int i = 0; i < float_a_n.length; i++) {
            floats[i] = new Float(float_a_n[i]);
        }
        return floats;
    }

    public static <T> boolean doesArrayContainAnValidObject(T[] array) {
        if (array.length <= 0) {
            return false;
        }
        boolean doesContainAnObject = true;
        for(T anObject : array) {
            if (anObject == null) {
                doesContainAnObject = false;
                break;
            }
        }
        return doesContainAnObject;
    }

    public static Integer convertByteToInteger(byte byte_n) {
        return new Integer(new Byte(byte_n).intValue());
    }

    public static String convertResourceLocationToString(ResourceLocation resource) {
        return "/assets/" + resource.getResourceDomain() + resource.getResourcePath();
    }

}
