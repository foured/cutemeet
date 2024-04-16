package com.foured.cutemeet.config;

import com.foured.cutemeet.models.EventData;
import com.foured.cutemeet.models.QuestionnaireData;

import java.io.Serializable;

public class BundleBuffer implements Serializable {
    public enum From{
        ViewEvent,
        QuestionnairePanel;
    }

    public From from;
    public EventData eventData;
}
