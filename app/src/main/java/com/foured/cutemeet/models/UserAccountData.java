package com.foured.cutemeet.models;

import java.io.Serializable;
import java.util.Base64;

public class UserAccountData implements Serializable {
    public byte[] photoData;
    public String tags;
    public String educationPlace;
    public String description;
    public String birthdayDate;
    public String tgLink;

    public String toJsonString(){
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        jsonBuilder.append("\"photoData\":\"").append(Base64.getEncoder().encodeToString(photoData)).append("\",");
        jsonBuilder.append("\"tags\":\"").append(tags).append("\",");
        jsonBuilder.append("\"educationPlace\":\"").append(educationPlace).append("\",");
        jsonBuilder.append("\"description\":\"").append(description).append("\",");
        jsonBuilder.append("\"birthdayDate\":\"").append(birthdayDate).append("\",");
        jsonBuilder.append("\"tgLink\":\"").append(tgLink).append("\"");

        jsonBuilder.append("}");

        return jsonBuilder.toString();
    }
}
