package com.ebe.qrscanner.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static String getCurrentDateTime(String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
            Date date = new Date();
            return formatter.format(date);
        } catch (Exception e) {
            return "";
        }
    }
    public static boolean validateWithRegex(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            return true;
        }
        return false;
    }
}
