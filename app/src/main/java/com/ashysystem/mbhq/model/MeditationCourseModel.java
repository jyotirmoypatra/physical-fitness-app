package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MeditationCourseModel implements Serializable {

    @SerializedName("Count")
    @Expose
    private Integer count;

    @SerializedName("ShowShopNow")
    @Expose
    private Boolean showShopNow;

    @SerializedName("ShoppingURL")
    @Expose
    private String shoppingURL;

    @SerializedName("Webinars")
    @Expose
    private List<Webinar> webinars = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Webinar> getWebinars() {
        return webinars;
    }

    public void setWebinars(List<Webinar> webinars) {
        this.webinars = webinars;
    }

    public Boolean getShowShopNow() {
        if (shoppingURL == null) {
            return false;
        }
        return showShopNow;
    }

    public String getShoppingURL() {
        if (shoppingURL == null) {
            return "";
        }
        return shoppingURL;
    }

    //////////////////////
    public static class Webinar implements Serializable {

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
        private String endDate;
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
        private List<String> tags = null;
        @SerializedName("Podcast")
        @Expose
        private List<String> Podcast = null;
        @SerializedName("VideoURLs")
        @Expose
        private Object videoURLs;
        @SerializedName("EventItemVideoDetails")
        @Expose
        private List<EventItemVideoDetail> eventItemVideoDetails = null;
        @SerializedName("LikeCount")
        @Expose
        private Integer likeCount;
        @SerializedName("CommentsCount")
        @Expose
        private Integer commentsCount;
        @SerializedName("DonationAmount")
        @Expose
        private Integer donationAmount;
        private long downloadid=0;

        public long getDownloadid() {
            return downloadid;
        }

        public void setDownloadid(long downloadid) {
            this.downloadid = downloadid;
        }

        public Integer getDuration() {
            if (Duration == null) {
                return 0;
            } else {
                return Duration;
            }
        }

        public void setDuration(Integer duration) {
            Duration = duration;
        }

        @SerializedName("Duration")
        @Expose
        private Integer Duration;
        @SerializedName("ImageUrl")
        @Expose
        private String imageUrl;
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
        private String fbUrl;
        @SerializedName("FbAppUrl")
        @Expose
        private String fbAppUrl;

        @SerializedName("Level")
        @Expose
        private Integer level;

        @SerializedName("NoCueUrl")
        @Expose
        private String noCueUrl;

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

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getEventName() {
            if (eventName == null) {
                return "";
            }
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
            if (tags == null) {
                return new ArrayList<String>();
            }
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> getPodcast() {
            return Podcast;
        }

        public void setPodcast(List<String> podcast) {
            this.Podcast = podcast;
        }

        public Object getVideoURLs() {
            return videoURLs;
        }

        public void setVideoURLs(Object videoURLs) {
            this.videoURLs = videoURLs;
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
            if (eventTypename == null) {
                return "";
            }
            return eventTypename;
        }

        public void setEventTypename(String eventTypename) {
            this.eventTypename = eventTypename;
        }

        public String getFbUrl() {
            return fbUrl;
        }

        public void setFbUrl(String fbUrl) {
            this.fbUrl = fbUrl;
        }

        public Object getFbAppUrl() {
            return fbAppUrl;
        }

        public void setFbAppUrl(String fbAppUrl) {
            this.fbAppUrl = fbAppUrl;
        }

        public Integer getLevel() {
            if (level == null) {
                return -1;
            }
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getNoCueUrl() {
            if (noCueUrl == null) {
                return "";
            }
            return noCueUrl;
        }

        public void setNoCueUrl(String noCueUrl) {
            this.noCueUrl = noCueUrl;
        }

        public String getGuided1Title() {
            if (guided1Title == null) {
                return "";
            }
            return guided1Title;
        }

        public String getGuided2Title() {

            if (guided2Title == null) {
                return "";
            }
            return guided2Title;
        }

        public String getGuided3Title() {

            if (guided3Title == null) {
                return "";
            }
            return guided3Title;
        }

        public String getGuided4Title() {

            if (guided4Title == null) {
                return "";
            }
            return guided4Title;
        }

        public String getGuided5Title() {

            if (guided5Title == null) {
                return "";
            }
            return guided5Title;
        }

        public String getGuided1Url() {

            if (guided1Url == null) {
                return "";
            }
            return guided1Url;
        }

        public String getGuided2Url() {

            if (guided2Url == null) {
                return "";
            }
            return guided2Url;
        }

        public String getGuided3Url() {

            if (guided3Url == null) {
                return "";
            }
            return guided3Url;
        }

        public String getGuided4Url() {

            if (guided4Url == null) {
                return "";
            }
            return guided4Url;
        }

        public String getGuided5Url() {

            if (guided5Url == null) {
                return "";
            }
            return guided5Url;
        }

        public String getUniqueName() {
            return getEventItemID() + "_" + getEventName() + "_" + getEventTypename();
        }

    }

    ////////////////////
    public static class EventItemVideoDetail {

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
        @SerializedName("MediaType")
        @Expose
        private String MediaType;

        public String getMediaType() {
            return MediaType;
        }

        public void setMediaType(String mediaType) {
            MediaType = mediaType;
        }

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

    }

}

