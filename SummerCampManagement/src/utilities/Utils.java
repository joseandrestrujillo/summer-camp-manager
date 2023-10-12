package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;


public class Utils {
	public static Date parseDate(String dateString) {
		try {
	         return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
	     } catch (ParseException e) {
	         return null;
	     }
	}

	public static String getStringDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	
	public static long daysBetween(Date zero, Date date) {
		long daysBetween = ChronoUnit.DAYS.between(zero.toInstant(), date.toInstant());
		boolean isBefore = zero.getTime() >= date.getTime();
		return Math.abs(daysBetween) * (isBefore ? 1 : -1);
	}
	
	public static long yearsBetween(Date zero, Date date) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(zero);
        int startYear = calendar.get(Calendar.YEAR);

        calendar.setTime(date);
        int endYear = calendar.get(Calendar.YEAR);

        return Math.abs(endYear - startYear);
	}
}
