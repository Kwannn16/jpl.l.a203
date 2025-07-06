package fa.training.utils;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isValidPhone(String phone) {
        return Pattern.matches("^(03|05|07|08|09)\\d{8}$", phone);
    }

    public static boolean isValidOrderNumber(String number) {
        return number != null && number.length() == 10 && number.matches("\\d+");
    }
}
