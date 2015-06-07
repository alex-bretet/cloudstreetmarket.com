package edu.zipcloud.core.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {
	
	public static Date getEndOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return calendar.getTime();
	}

	public static Date getStartOfDay(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	
	public static Date getXMinPriorDate(Date date, int nbMinutes) {
	    return DateUtils.addMinutes(date, -1*nbMinutes);
	}

	public static boolean isRecent(Date date, int nbMinutes) {
		if(date==null){
			throw new IllegalArgumentException("date parameter cannot be null!");
		}
		return date.after(getXMinPriorDate(new Date(), nbMinutes));
	}
}
