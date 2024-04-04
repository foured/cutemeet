package com.foured.cutemeet.algorithms;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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

    public static <T> List<T> parseJsonArray(String jsonArrayString, Class<T> classOfT) {
        List<T> resultList = new ArrayList<>();

        Gson gson = new Gson();
        JsonArray jsonArray = JsonParser.parseString(jsonArrayString).getAsJsonArray();

        for (JsonElement jsonElement : jsonArray) {
            T object = gson.fromJson(jsonElement, classOfT);
            resultList.add(object);
        }

        return resultList;
    }

    public static <T> T parseJsonClass(String jsonObjectString, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObjectString, classOfT);
    }
}
