package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
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
	
	public static long daysBetween(Date newest, Date oldest) {
		return ChronoUnit.DAYS.between(newest.toInstant(), oldest.toInstant());
	}
}
