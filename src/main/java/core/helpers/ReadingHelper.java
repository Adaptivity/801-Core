package core.helpers;

import java.io.BufferedReader;

/**
 * Created by Master801 on 12/7/2014 at 9:11 AM.
 * @author Master801
 */
public final class ReadingHelper {

    public static String readLineFromBufferedReader(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void closeBufferedReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
