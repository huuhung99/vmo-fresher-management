package devil.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
	
    public static String convertDateToString(Date inputDate, String format) {    	
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(inputDate);
    }
      
    public static Date convertStringToDate(String inputDate, String format) throws Exception{
        DateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(inputDate);
    }
}
