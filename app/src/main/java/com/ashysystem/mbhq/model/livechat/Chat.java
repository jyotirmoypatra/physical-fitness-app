package com.ashysystem.mbhq.model.livechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {

    @SerializedName("Meditations")
    @Expose
    private List<Meditations> Meditations;

    @SerializedName("EventItemID")
    @Expose
    private int EventItemID;

    @SerializedName("GlyphName")
    @Expose
    private String glyphName;

    @SerializedName("StartDate")
    @Expose
    private String startDate;

    @SerializedName("EndDate")
    @Expose
    private String endDate;

    @SerializedName("Duration")
    @Expose
    private int Duration;

    @SerializedName("EventName")
    @Expose
    private String EventName;

    @SerializedName("PresenterName")
    @Expose
    private String PresenterName;


    @SerializedName("Content")
    @Expose
    private String Content;

    @SerializedName("Tags")
    @Expose
    private List<String> tags;

    @SerializedName("EventItemVideoDetails")
    @Expose
    private List<EventItemVideoDetail> eventItemVideoDetails;

    @SerializedName("ImageUrl")
    @Expose
    private String ImageUrl;

    @SerializedName("EventTypeDescription")
    @Expose
    private String eventTypeDescription;

    @SerializedName("EventTypename")
    @Expose
    private String eventTypename;

    @SerializedName("FbUrl")
    @Expose
    private String fbUrl;

    @SerializedName("FbAppUrl")
    @Expose
    private String fbAppUrl;

    @SerializedName("Level")
    @Expose
    private String level;

    public int getEventItemID() {
        return EventItemID;
    }

    public String getGlyphName() {
        return glyphName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEventName() {
        return EventName;
    }

    public String getEventTypename() {
        return eventTypename;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<EventItemVideoDetail> getEventItemVideoDetails() {
        return eventItemVideoDetails;
    }

    public String getContent() {
        return Content;
    }

    public String getEventTypeDescription() {
        return eventTypeDescription;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getPresenterName() {
        return PresenterName;
    }

    public String getFbAppUrl() {
        return fbAppUrl;
    }

    public String getLevel() {
        return level;
    }

    public int getDuration() {
        return Duration;
    }

    public List<Meditations> getMeditations() {
        return Meditations;
    }

    public void setMeditations(List<Meditations> meditations) {
        Meditations = meditations;
    }



}
