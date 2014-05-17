package net.simplyadvanced.simplytonegenerator;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.Time;
import android.util.Log;

public class HelperTime {
	
	public static String getCurrentDate() {
		// Method 1
//		Time timeNow = new Time(Time.getCurrentTimezone());
//		timeNow.setToNow();
//		
//		int currentYear = timeNow.year; // Returns 1970+
//		int currentMonth = timeNow.month; // Returns 0-11
//		int currentDay = timeNow.monthDay; // Returns 1-31
//		
//		String currentDate = "" + currentYear + "-" + (currentMonth + 1) + "-" + currentDay;
		
		// Method 2
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Could be "yyyyMMdd_HHmmss".
		String currentDate = sdf.format(new Date());
		
//		Log.d("DEBUG", "currentDate: " + currentDate);
		return currentDate;
	}
	
	public static String getCurrentMeal() {
		long currentTimeHour = getCurrentTimeHour();
		
		if (currentTimeHour >= 3 && currentTimeHour <= 9) {
			return "breakfast";
		} else if (currentTimeHour >= 10 && currentTimeHour <= 16) {
			return "lunch";
		} else {
			return "dinner";
		}
	}
	
	public static long getCurrentTimeHour() {
		Time timeNow = new Time(Time.getCurrentTimezone());
		timeNow.setToNow();
		return timeNow.hour;
	}
	
	public static long getCurrentTimeMillis() {
		Time timeNow = new Time(Time.getCurrentTimezone());
		timeNow.setToNow();
		return timeNow.toMillis(false);
	}
	
}
