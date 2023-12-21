
package com.ashysystem.mbhq.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Tag {

    @SerializedName("EventTagID")
    @Expose
    private Integer eventTagID;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("TotalEvents")
    @Expose
    private Integer totalEvents;

    public Integer getEventTagID() {
        return eventTagID;
    }

    public void setEventTagID(Integer eventTagID) {
        this.eventTagID = eventTagID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Integer totalEvents) {
        this.totalEvents = totalEvents;
    }

}
