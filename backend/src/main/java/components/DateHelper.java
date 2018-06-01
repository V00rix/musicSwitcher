package components;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateHelper {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    public static Date toDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            System.out.println("Not a valid date");
            e.printStackTrace();
            return null;
        }
    }

    public static int compare(String d1, String d2) {
        Date date1 = toDate(d1);
        Date date2 = toDate(d2);
        return date1 != null && date2 != null ? date1.compareTo(date2) : 0;
    }
}
