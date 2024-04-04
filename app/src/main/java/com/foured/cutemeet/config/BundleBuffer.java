package com.foured.cutemeet.config;

import com.foured.cutemeet.models.EventData;

import java.io.Serializable;

public class BundleBuffer implements Serializable {
    public enum From{
        ViewEvent
    }

    public From from;
    public EventData eventData;

}
