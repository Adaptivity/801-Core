package core.helpers;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Master801
 */
public class CalenderHelper {

	private static final Calendar CALENDER = Calendar.getInstance();

	public static Calendar getCalender() {
        Date date = new Date();
        if (CALENDER.getTime() != date) {
            CALENDER.setTime(new Date());
        }
        return CALENDER;
	}

	public static boolean isChristmas() {
        return CalenderHelper.getCalender().get(Calendar.MONTH) == Calendar.DECEMBER && CalenderHelper.getCalender().get(Calendar.DATE) == 24;
	}

	public static boolean isHalloween() {
        return CalenderHelper.getCalender().get(Calendar.MONTH) == Calendar.OCTOBER && CalenderHelper.getCalender().get(Calendar.DATE) == 31;
	}

	public static boolean isNewYear() {
		return CalenderHelper.getCalender().get(Calendar.MONTH) == Calendar.JANUARY && CalenderHelper.getCalender().get(Calendar.DATE) == 1;
	}

	public static boolean isNotchsBirthday() {
		return CalenderHelper.getCalender().get(Calendar.MONTH) == Calendar.JULY && CalenderHelper.getCalender().get(Calendar.DATE) == 1;
	}

	public static boolean isMaster801sBirthday() {
		return CalenderHelper.getCalender().get(Calendar.MONTH) == Calendar.JUNE && CalenderHelper.getCalender().get(Calendar.DATE) == 15;
	}

}
