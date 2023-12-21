
package com.ashysystem.mbhq.model.response.suggestedmedicin;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeditationDetails implements Serializable {

    @SerializedName("EventItemID")
    @Expose
    private Integer eventItemID;
    @SerializedName("EventType")
    @Expose
    private Integer eventType;
    @SerializedName("GlyphName")
    @Expose
    private String glyphName;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private Object endDate;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("EventName")
    @Expose
    private String eventName;
    @SerializedName("PresenterName")
    @Expose
    private String presenterName;
    @SerializedName("Content")
    @Expose
    private String content;
    @SerializedName("Tags")
    @Expose
    private List<String> tags;
    @SerializedName("EventItemVideoDetails")
    @Expose
    private List<EventItemVideoDetail> eventItemVideoDetails;
    @SerializedName("LikeCount")
    @Expose
    private Integer likeCount;
    @SerializedName("CommentsCount")
    @Expose
    private Integer commentsCount;
    @SerializedName("DonationAmount")
    @Expose
    private Integer donationAmount;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Attachments")
    @Expose
    private List<Object> attachments;
    @SerializedName("EventTypeDescription")
    @Expose
    private Object eventTypeDescription;
    @SerializedName("Likes")
    @Expose
    private Boolean likes;
    @SerializedName("EventTypename")
    @Expose
    private String eventTypename;
    @SerializedName("FbUrl")
    @Expose
    private Object fbUrl;
    @SerializedName("FbAppUrl")
    @Expose
    private Object fbAppUrl;
    @SerializedName("Level")
    @Expose
    private Integer level;
    @SerializedName("NoCueUrl")
    @Expose
    private Object noCueUrl;
    @SerializedName("Guided1Title")
    @Expose
    private String guided1Title;
    @SerializedName("Guided1Url")
    @Expose
    private String guided1Url;
    @SerializedName("Guided2Title")
    @Expose
    private String guided2Title;
    @SerializedName("Guided2Url")
    @Expose
    private String guided2Url;
    @SerializedName("Guided3Title")
    @Expose
    private String guided3Title;
    @SerializedName("Guided3Url")
    @Expose
    private String guided3Url;
    @SerializedName("Guided4Title")
    @Expose
    private String guided4Title;
    @SerializedName("Guided4Url")
    @Expose
    private String guided4Url;
    @SerializedName("Guided5Title")
    @Expose
    private String guided5Title;
    @SerializedName("Guided5Url")
    @Expose
    private String guided5Url;

    public Integer getEventItemID() {
        return eventItemID;
    }

    public void setEventItemID(Integer eventItemID) {
        this.eventItemID = eventItemID;
    }

    public Integer getEventType() {
        return eventType;
    }

    public void setEventType(Integer eventType) {
        this.eventType = eventType;
    }

    public String getGlyphName() {
        return glyphName;
    }

    public void setGlyphName(String glyphName) {
        this.glyphName = glyphName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<EventItemVideoDetail> getEventItemVideoDetails() {
        return eventItemVideoDetails;
    }

    public void setEventItemVideoDetails(List<EventItemVideoDetail> eventItemVideoDetails) {
        this.eventItemVideoDetails = eventItemVideoDetails;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Integer donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public Object getEventTypeDescription() {
        return eventTypeDescription;
    }

    public void setEventTypeDescription(Object eventTypeDescription) {
        this.eventTypeDescription = eventTypeDescription;
    }

    public Boolean getLikes() {
        return likes;
    }

    public void setLikes(Boolean likes) {
        this.likes = likes;
    }

    public String getEventTypename() {
        return eventTypename;
    }

    public void setEventTypename(String eventTypename) {
        this.eventTypename = eventTypename;
    }

    public Object getFbUrl() {
        return fbUrl;
    }

    public void setFbUrl(Object fbUrl) {
        this.fbUrl = fbUrl;
    }

    public Object getFbAppUrl() {
        return fbAppUrl;
    }

    public void setFbAppUrl(Object fbAppUrl) {
        this.fbAppUrl = fbAppUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Object getNoCueUrl() {
        return noCueUrl;
    }

    public void setNoCueUrl(Object noCueUrl) {
        this.noCueUrl = noCueUrl;
    }

    public String getGuided1Title() {
        return guided1Title;
    }

    public void setGuided1Title(String guided1Title) {
        this.guided1Title = guided1Title;
    }

    public String getGuided1Url() {
        return guided1Url;
    }

    public void setGuided1Url(String guided1Url) {
        this.guided1Url = guided1Url;
    }

    public String getGuided2Title() {
        return guided2Title;
    }

    public void setGuided2Title(String guided2Title) {
        this.guided2Title = guided2Title;
    }

    public String getGuided2Url() {
        return guided2Url;
    }

    public void setGuided2Url(String guided2Url) {
        this.guided2Url = guided2Url;
    }

    public String getGuided3Title() {
        return guided3Title;
    }

    public void setGuided3Title(String guided3Title) {
        this.guided3Title = guided3Title;
    }

    public String getGuided3Url() {
        return guided3Url;
    }

    public void setGuided3Url(String guided3Url) {
        this.guided3Url = guided3Url;
    }

    public String getGuided4Title() {
        return guided4Title;
    }

    public void setGuided4Title(String guided4Title) {
        this.guided4Title = guided4Title;
    }

    public String getGuided4Url() {
        return guided4Url;
    }

    public void setGuided4Url(String guided4Url) {
        this.guided4Url = guided4Url;
    }

    public String getGuided5Title() {
        return guided5Title;
    }

    public void setGuided5Title(String guided5Title) {
        this.guided5Title = guided5Title;
    }

    public String getGuided5Url() {
        return guided5Url;
    }

    public void setGuided5Url(String guided5Url) {
        this.guided5Url = guided5Url;
    }

}
