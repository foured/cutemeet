package com.foured.cutemeet.models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class EventData implements Serializable {
    public String name;
    public String senderName;
    public String senderContact;
    public String date;
    public String location;
    public String tags;

    public String toJsonString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        jsonBuilder.append("\"name\":\"").append(name).append("\",");
        jsonBuilder.append("\"senderName\":\"").append(senderName).append("\",");
        jsonBuilder.append("\"senderContact\":\"").append(senderContact).append("\",");
        jsonBuilder.append("\"date\":\"").append(date).append("\",");
        jsonBuilder.append("\"location\":\"").append(location).append("\",");
        jsonBuilder.append("\"tags\":\"").append("").append("\"");

//        jsonBuilder.append("\"tags\":[");
//        if (tags != null && !tags.isEmpty()) {
//            for (int i = 0; i < tags.size(); i++) {
//                jsonBuilder.append("\"").append(tags.get(i)).append("\"");
//                if (i < tags.size() - 1) {
//                    jsonBuilder.append(",");
//                }
//            }
//        }
//        jsonBuilder.append("]");

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    public static EventData fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, EventData.class);
    }
}
