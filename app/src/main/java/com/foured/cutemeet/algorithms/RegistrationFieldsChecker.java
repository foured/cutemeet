package com.foured.cutemeet.algorithms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationFieldsChecker {
    public static boolean isPhoneNumber(CharSequence s) {
        return Pattern.matches("[+\\d]+", s) && s.length() == 12;
    }
    public static boolean isEmailAddress(CharSequence s){
        return Pattern.matches(".*@.*", s) && Pattern.matches(".*\\..*", s) &&  s.length() >= 4;
    }

    public static boolean isTextLine(CharSequence s){
        return (Pattern.matches("^[a-zA-Z]+$", s) || Pattern.matches("^[а-яА-Я]+$", s) ) && s.length() >= 2;
    }

    public static boolean isUsername(CharSequence s){
        return Pattern.matches("^[^@]*[a-zA-Zа-яА-Я]+[^@]*$", s) && s.length() >= 3;
    }

    public static boolean isPassword(CharSequence s){
        return s.length() >= 4;
    }

    public static boolean isDate(CharSequence s){
        String regex = "^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(\\d{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
