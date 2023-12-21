package com.ashysystem.mbhq.model.livechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Meditations implements Serializable {
    @SerializedName("EventItemId")
    @Expose
    private int EventItemId;


    @SerializedName("EventName")
    @Expose
    private String EventName;

    public int getEventItemId() {
        return EventItemId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventItemId(int eventItemId) {
        EventItemId = eventItemId;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }
}
