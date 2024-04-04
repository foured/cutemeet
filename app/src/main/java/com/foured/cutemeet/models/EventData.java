package com.foured.cutemeet.models;

import com.google.gson.Gson;

import java.io.Serializable;

public class EventData implements Serializable {
    public String name;
    public String date;
    public String username;
    public String location;
    public String description;
    public String tags;

    public String toJsonString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        jsonBuilder.append("\"name\":\"").append(name).append("\",");
        jsonBuilder.append("\"senderName\":\"").append("").append("\",");
        jsonBuilder.append("\"date\":\"").append(date).append("\",");
        jsonBuilder.append("\"location\":\"").append(location).append("\",");
        jsonBuilder.append("\"description\":\"").append(description).append("\",");
        jsonBuilder.append("\"tags\":\"").append(tags).append("\"");

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    public static EventData fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, EventData.class);
    }
}
