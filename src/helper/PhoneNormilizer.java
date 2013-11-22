
package helper;


public class PhoneNormilizer {
    
    final private static int MAX_PHONE_SIZE = 10;
    final private static String[] areaCodeWithExtraNumber = new String[] {
      "11"
    };
    
    public static String normilize(String phone) {
        for (String code : areaCodeWithExtraNumber) {
            if (phone.startsWith(code) && phone.length() > MAX_PHONE_SIZE) {
                phone = removeExtraCodeFromPhone(phone);
                break;
            }
        }
        if (phone.length() >= MAX_PHONE_SIZE) {
            phone = phone.substring(0, MAX_PHONE_SIZE);
        }
        return phone;
    }

    private static String removeExtraCodeFromPhone(String phone) {
        StringBuilder builder = new StringBuilder();
        builder.append(phone.substring(0, 2));
        builder.append(phone.substring(3, phone.length()));
        return builder.toString();
    }
}
