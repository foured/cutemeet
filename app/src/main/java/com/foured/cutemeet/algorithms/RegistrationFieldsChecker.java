package com.foured.cutemeet.algorithms;

import java.util.regex.Pattern;

public class RegistrationFieldsChecker {
    public static boolean isPhoneNumber(CharSequence s){
        return Pattern.matches("\\d+", s) && s.length() == 11;
    }
    public static boolean isEmailAddress(CharSequence s){
        return Pattern.matches(".*@.*", s) && Pattern.matches(".*\\..*", s) &&  s.length() >= 4;
    }
}
