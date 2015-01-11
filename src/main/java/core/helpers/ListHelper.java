package core.helpers;

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

}
