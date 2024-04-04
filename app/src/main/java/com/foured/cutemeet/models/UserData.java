package com.foured.cutemeet.models;

import com.google.gson.Gson;

import java.io.Serializable;

public class UserData implements Serializable {
    public String userName;
    public String email;
    public String phoneNumber;
    public String password;

    public String name;
    public String middleName;
    public String surname;

    public String toJsonString(){
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        jsonBuilder.append("\"userName\":\"").append(userName).append("\",");
        jsonBuilder.append("\"email\":\"").append(email).append("\",");
        jsonBuilder.append("\"phoneNumber\":\"").append(phoneNumber).append("\",");
        jsonBuilder.append("\"password\":\"").append(password).append("\",");
        jsonBuilder.append("\"name\":\"").append(name).append("\",");
        jsonBuilder.append("\"middleName\":\"").append(middleName).append("\",");
        jsonBuilder.append("\"surname\":\"").append(surname).append("\",");
        jsonBuilder.append("\"roles\":\"").append("ROLE_USER").append("\"");

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }

    public static EventData fromJson(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, EventData.class);
    }
}
