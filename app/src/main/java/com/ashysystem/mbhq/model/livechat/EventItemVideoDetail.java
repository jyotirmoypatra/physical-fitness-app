package com.ashysystem.mbhq.model.livechat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventItemVideoDetail implements Serializable {

    @SerializedName("EventItemVideoID")
    @Expose
    private int eventItemVideoID;

    @SerializedName("SequenceNo")
    @Expose
    private int sequenceNo;

    @SerializedName("VideoURL")
    @Expose
    private String videoURL;

    @SerializedName("AppURL")
    @Expose
    private String appURL;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @SerializedName("Time")
    @Expose
    private String Time;

    @SerializedName("DownloadURL")
    @Expose
    private String downloadURL;

    @SerializedName("PodcastURL")
    @Expose
    private String podcastURL;

    @SerializedName("EventDetail")
    @Expose
    private Object eventDetail;

    @SerializedName("IsWatchListVideo")
    @Expose
    private Boolean isWatchListVideo;

    @SerializedName("IsViewedVideo")
    @Expose
    private Object isViewedVideo;

    public Integer getEventItemVideoID() {
        return eventItemVideoID;
    }

    public void setEventItemVideoID(Integer eventItemVideoID) {
        this.eventItemVideoID = eventItemVideoID;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getAppURL() {
        return appURL;
    }

    public void setAppURL(String appURL) {
        this.appURL = appURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getPodcastURL() {
        return podcastURL;
    }

    public void setPodcastURL(String podcastURL) {
        this.podcastURL = podcastURL;
    }

    public Object getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(Object eventDetail) {
        this.eventDetail = eventDetail;
    }

    public Boolean getIsWatchListVideo() {
        return isWatchListVideo;
    }

    public void setIsWatchListVideo(Boolean isWatchListVideo) {
        this.isWatchListVideo = isWatchListVideo;
    }

    public Object getIsViewedVideo() {
        return isViewedVideo;
    }

    public void setIsViewedVideo(Object isViewedVideo) {
        this.isViewedVideo = isViewedVideo;
    }



   /* public final static Creator<EventItemVideoDetail> CREATOR = new Creator<EventItemVideoDetail>() {

        @SuppressWarnings({
                "unchecked"
        })
        public EventItemVideoDetail createFromParcel(Parcel in) {
            return new EventItemVideoDetail(in);
        }

        public EventItemVideoDetail[] newArray(int size) {
            return (new EventItemVideoDetail[size]);
        }

    };

    protected EventItemVideoDetail(Parcel in) {
        this.eventItemVideoID = in.readInt();
        this.sequenceNo = in.readInt();
        this.isWatchListVideo = (boolean) in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }*/
}
