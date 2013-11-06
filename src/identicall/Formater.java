package identicall;

import java.util.Calendar;

public class Formater {
    
    public static String formatRecentCall(String phone, Calendar calendar) {
        return formatPhone(phone) + " @ " + formatDateTime(calendar);
    }

    public static String formatPhone(String phone) {
        if (phone.length() < 10) {
            return phone;
        }
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append("(");
        toDisplay.append(phone.substring(0, 2));
        toDisplay.append(") ");
        toDisplay.append(phone.substring(2, 10));
        return toDisplay.toString();
    }

    public static String formatDateTime(Calendar calendar) {
        StringBuilder toDisplay = new StringBuilder();
        toDisplay.append(String.format("%02d", calendar.get(Calendar.HOUR)));
        toDisplay.append(":");
        toDisplay.append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        toDisplay.append(" ");
        toDisplay.append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        toDisplay.append("/");
        toDisplay.append(String.format("%02d", calendar.get(Calendar.MONTH)));
        toDisplay.append("/");
        toDisplay.append(String.format("%04d", calendar.get(Calendar.YEAR)));
        return toDisplay.toString();
    }
    
}
