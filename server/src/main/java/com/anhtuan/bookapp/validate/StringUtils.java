package com.anhtuan.bookapp.validate;


import java.util.List;

public class StringUtils {

    public static boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isValid(String... strings) {
        for (String string : strings) {
            if (!isValid(string)) {
                return false;
            }
        }
        return true;
    }


    public static boolean isValid(List<String> strings) {

        if (strings == null || strings.isEmpty()) {
            return false;
        }
        for (String string : strings) {
            if (!isValid(string)) {
                return false;
            }
        }
        return true;
    }

}
