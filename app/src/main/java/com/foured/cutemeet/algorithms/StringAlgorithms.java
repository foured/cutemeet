package com.foured.cutemeet.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringAlgorithms {
    public static List<String> parseStringForTags(String input) {
        List<String> result = new ArrayList<>();

        if (input != null && !input.isEmpty()) {
            Pattern pattern = Pattern.compile("\\s*,\\s*");
            String[] values = pattern.split(input);

            for (String value : values) {
                result.add(value.trim());
            }
        }

        return result;
    }
}
