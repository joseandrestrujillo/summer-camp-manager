package app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	public static Date parseDate(String dateString) {
		try {
	         return new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
	     } catch (ParseException e) {
	         return null;
	     }
	}
}
