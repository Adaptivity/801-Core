package core.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master801 on 12/7/2014 at 9:14 AM.
 * @author Master801
 */
public final class ListHelper {

    /**
     * This checks if the object is currently existing within the list, and adds it to the list if there is not an existing value.
     */
    public static <T> void addObjectToListWhileChecking(List<T> list, T object) {
        if (!list.contains(object)) {
            list.add(object);
        }
    }

    public static <T> List<T> convertArrayToNewList(T[] objectArray) {
        return ListHelper.convertArrayToExistingList(objectArray, new ArrayList<T>());
    }

    public static <T> List<T> convertArrayToExistingList(T[] objectArray, List<T> existing) {
        for(T object : objectArray) {
            ListHelper.addObjectToListWhileChecking(existing, object);
        }
        return existing;
    }

    public static <T> T[] convertListToArray(List<T> list) {
        if (list.size() < 1) {
            return (T[])new Object[list.size()];
        }
        T[] objectArray = (T[])new Object[list.size()];
        for(int i = 0; i < objectArray.length; i++) {
            T object = list.get(i);
            if (object != null) {
                objectArray[i] = object;
            }
        }
        if (objectArray.length > 0) {
            return objectArray;
        }
        return null;
    }

}
