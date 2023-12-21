package com.ashysystem.mbhq.model;

import java.util.ArrayList;

/**
 * Created by biswajit on 21/6/16.
 */
public class ArchivedWebinalModel {

    private String EventName;
    private String PresenterName;
    private String Content;
    private String EventDate;
    private String ImageUrl;
    private String LikeCount;
    private String EventID;
    private String EventItemVideoID;
    private String DownloadURL;
    private boolean Likes;
    private ArrayList<String> arrVideoUrl;
    private ArrayList<String> arrTags;
    private ArrayList<Boolean> arrWatchBool;
    private ArrayList<String> arrPodcastURL;
    private String EventType;
    private boolean IsUpcomingEvent;
    private boolean IsViewedVideo;
    private String EventTypeDescription;
    private int DownloadedFileSize;
    private String EventTypename;
    private String FbUrl;
    private String FbAppUrl;

    public String getEventTypename() {
        return EventTypename;
    }

    public void setEventTypename(String eventTypename) {
        EventTypename = eventTypename;
    }

    public String getFbUrl() {
        return FbUrl;
    }

    public void setFbUrl(String fbUrl) {
        FbUrl = fbUrl;
    }

    public String getFbAppUrl() {
        return FbAppUrl;
    }

    public void setFbAppUrl(String fbAppUrl) {
        FbAppUrl = fbAppUrl;
    }

    public int getDownloadedFileSize() {
        return DownloadedFileSize;
    }

    public void setDownloadedFileSize(int downloadedFileSize) {
        DownloadedFileSize = downloadedFileSize;
    }




    public boolean isViewedVideo() {
        return IsViewedVideo;
    }

    public void setViewedVideo(boolean viewedVideo) {
        IsViewedVideo = viewedVideo;
    }




    public String getEventTypeDescription() {
        return EventTypeDescription;
    }

    public void setEventTypeDescription(String eventTypeDescription) {
        EventTypeDescription = eventTypeDescription;
    }

    public boolean isUpcomingEvent() {
        return IsUpcomingEvent;
    }

    public void setUpcomingEvent(boolean upcomingEvent) {
        IsUpcomingEvent = upcomingEvent;
    }

    public String getEventType() {
        return EventType;
    }

    public void setEventType(String eventType) {
        EventType = eventType;
    }


    public ArrayList<String> getArrPodcastURL() {
        return arrPodcastURL;
    }

    public void setArrPodcastURL(ArrayList<String> arrPodcastURL) {
        this.arrPodcastURL = arrPodcastURL;
    }


    public String getDownloadURL() {
        return DownloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        DownloadURL = downloadURL;
    }

    public String getEventItemVideoID() {
        return EventItemVideoID;
    }

    public void setEventItemVideoID(String eventItemVideoID) {
        EventItemVideoID = eventItemVideoID;
    }

    public ArrayList<Boolean> getArrWatchBool() {
        return arrWatchBool;
    }

    public void setArrWatchBool(ArrayList<Boolean> arrWatchBool) {
        this.arrWatchBool = arrWatchBool;
    }

    public String getEventID() {
        return EventID;
    }

    public void setEventID(String eventID) {
        EventID = eventID;
    }

    public ArrayList<String> getArrTags() {
        return arrTags;
    }

    public void setArrTags(ArrayList<String> arrTags) {
        this.arrTags = arrTags;
    }

    public ArrayList<String> getArrVideoUrl() {
        return arrVideoUrl;
    }

    public void setArrVideoUrl(ArrayList<String> arrVideoUrl) {
        this.arrVideoUrl = arrVideoUrl;
    }


    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getPresenterName() {
        return PresenterName;
    }

    public void setPresenterName(String presenterName) {
        PresenterName = presenterName;
    }

    public String getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(String likeCount) {
        LikeCount = likeCount;
    }

    public boolean isLikes() {
        return Likes;
    }

    public void setLikes(boolean likes) {
        Likes = likes;
    }
}
