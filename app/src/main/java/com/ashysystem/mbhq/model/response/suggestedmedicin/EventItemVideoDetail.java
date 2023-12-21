
package com.ashysystem.mbhq.model.response.suggestedmedicin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventItemVideoDetail implements Serializable {

    @SerializedName("EventItemVideoID")
    @Expose
    private Integer eventItemVideoID;
    @SerializedName("SequenceNo")
    @Expose
    private Integer sequenceNo;
    @SerializedName("VideoURL")
    @Expose
    private String videoURL;
    @SerializedName("AppURL")
    @Expose
    private String appURL;
    @SerializedName("DownloadURL2")
    @Expose
    private String downloadURL2;
    @SerializedName("DownloadURLResolved")
    @Expose
    private String downloadURLResolved;
    @SerializedName("DownloadURL")
    @Expose
    private String downloadURL;
    @SerializedName("PodcastURL")
    @Expose
    private String podcastURL;
    @SerializedName("IsWatchListVideo")
    @Expose
    private Boolean isWatchListVideo;
    @SerializedName("IsViewedVideo")
    @Expose
    private Boolean isViewedVideo;
    @SerializedName("Time")
    @Expose
    private String time;

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

    public String getDownloadURL2() {
        return downloadURL2;
    }

    public void setDownloadURL2(String downloadURL2) {
        this.downloadURL2 = downloadURL2;
    }

    public String getDownloadURLResolved() {
        return downloadURLResolved;
    }

    public void setDownloadURLResolved(String downloadURLResolved) {
        this.downloadURLResolved = downloadURLResolved;
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

    public Boolean getIsWatchListVideo() {
        return isWatchListVideo;
    }

    public void setIsWatchListVideo(Boolean isWatchListVideo) {
        this.isWatchListVideo = isWatchListVideo;
    }

    public Boolean getIsViewedVideo() {
        return isViewedVideo;
    }

    public void setIsViewedVideo(Boolean isViewedVideo) {
        this.isViewedVideo = isViewedVideo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
