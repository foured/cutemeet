package com.foured.cutemeet.algorithms;

import java.util.regex.Pattern;

public class RegistrationFieldsChecker {
    public static boolean isPhoneNumber(CharSequence s){
        return Pattern.matches("\\d+", s) && s.length() == 11;
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
}
