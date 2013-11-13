package helper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class Formater {

    public static String formatRecentCall(String phone, Calendar calendar) {
        return formatPhone(phone) + " @ " + formatDateTime(calendar);
    }

    public static String formatPhone(String phone) {
        if (phone == null) {
            return "";
        }
        if (phone.length() < 10) {
            return phone;
        }
        StringBuilder result = new StringBuilder();
        result.append("(");
        result.append(phone.substring(0, 2));
        result.append(") ");
        result.append(phone.substring(2, 6));
        result.append("-");
        result.append(phone.substring(6, 10));
        return result.toString();
    }

    public static String formatDateTime(Calendar calendar) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%02d", calendar.get(Calendar.HOUR)));
        result.append(":");
        result.append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        result.append(" ");
        result.append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        result.append("/");
        result.append(String.format("%02d", calendar.get(Calendar.MONTH)));
        result.append("/");
        result.append(String.format("%04d", calendar.get(Calendar.YEAR)));
        return result.toString();
    }

    public static String formatCpfCnpj(String number) {
        if (number == null) {
            return "";
        }
        if (number.length() == 11) {
            return formatCpf(number);
        }
        if (number.length() == 14) {
            return formatCnpj(number);
        }
        return number;
    }

    /**
     * 584.658.251-55
     *
     * @param number
     * @return
     */
    private static String formatCpf(String number) {
        StringBuilder result = new StringBuilder();
        result.append(number.substring(0, 3));
        result.append(".");
        result.append(number.substring(3, 6));
        result.append(".");
        result.append(number.substring(6, 9));
        result.append("-");
        result.append(number.substring(9, 11));
        return result.toString();
    }

    /**
     * 01.234.569/9052-34
     *
     * @param number
     * @return
     */
    private static String formatCnpj(String number) {
        StringBuilder result = new StringBuilder();
        result.append(number.substring(0, 2));
        result.append(".");
        result.append(number.substring(2, 5));
        result.append(".");
        result.append(number.substring(5, 8));
        result.append("/");
        result.append(number.substring(8, 12));
        result.append("-");
        result.append(number.substring(12, 14));
        return result.toString();
    }

    public static String removeFormatation(String formated) {
        String regex = "[/()-. ]";
        return formated.replaceAll(regex, "");
    }

    public static String formatStringDate(String dateString) {
        if (dateString == null) {
            return "";
        }
        if (dateString.length() < 21) {
            return dateString;
        }
        String[] parts = dateString.split(" ");
        if (parts.length < 2) {
            return dateString;
        }
        List<String> datePart = Arrays.asList(parts[0].split("-"));
        Collections.reverse(datePart);
        StringBuilder result = new StringBuilder();
        int i = 1;
        for (String part : datePart) {
            result.append(part);
            if (i++ < datePart.size()) {
                result.append("/");
            }
        }
        return result.toString();
    }
}
