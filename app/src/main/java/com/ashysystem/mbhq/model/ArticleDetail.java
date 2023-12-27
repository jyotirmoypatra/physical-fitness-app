package com.ashysystem.mbhq.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-krishnendu on 2/23/17.
 */

public class ArticleDetail {

    @SerializedName("ArticleId")
    @Expose
    private Integer articleId;
    @SerializedName("ArticleTitle")
    @Expose
    private String articleTitle;
    @SerializedName("WeekNumber")
    @Expose
    private Integer weekNumber;
    @SerializedName("PreVideoText")
    @Expose
    private String preVideoText;
    @SerializedName("ArticleText")
    @Expose
    private String articleText;
    @SerializedName("Videos")
    @Expose
    private List<String> videos = null;
    @SerializedName("PublicVideoUrls")
    @Expose
    private List<String> publicVideoUrls = null;
    @SerializedName("Audios")
    @Expose
    private List<String> audios = null;
    @SerializedName("Images")
    @Expose
    private List<Object> images = null;
    @SerializedName("Attachments")
    @Expose
    private List<String> attachments = null;
    @SerializedName("RelatedTask")
    @Expose
    private RelatedTask relatedTask;
    @SerializedName("IsRead")
    @Expose
    private Boolean isRead;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    @SerializedName("Time")
    @Expose
    private String Time;
    @SerializedName("VideoWithTime")
    @Expose
    private List<VideoWithTime> videoWithTime = null;
    @SerializedName("Meditations")
    @Expose
    private List<Meditations> Meditations = null;

    public List<ArticleDetail.Meditations> getMeditations() {
        return Meditations;
    }

    public void setMeditations(List<ArticleDetail.Meditations> meditations) {
        Meditations = meditations;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Integer getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(Integer weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getPreVideoText() {
        return preVideoText;
    }

    public void setPreVideoText(String preVideoText) {
        this.preVideoText = preVideoText;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public List<String> getPublicVideoUrls() {
        return publicVideoUrls;
    }

    public void setPublicVideoUrls(List<String> publicVideoUrls) {
        this.publicVideoUrls = publicVideoUrls;
    }

    public List<String> getAudios() {
        return audios;
    }

    public void setAudios(List<String> audios) {
        this.audios = audios;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public RelatedTask getRelatedTask() {
        return relatedTask;
    }

    public void setRelatedTask(RelatedTask relatedTask) {
        this.relatedTask = relatedTask;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public List<VideoWithTime> getVideoWithTime() {
        return videoWithTime;
    }

    public void setVideoWithTime(List<VideoWithTime> videoWithTime) {
        this.videoWithTime = videoWithTime;
    }

    public class Meditations {

        @SerializedName("EventItemId")
        @Expose
        private Integer EventItemId;
        @SerializedName("EventName")
        @Expose
        private String EventName;

        public Integer getEventItemId() {
            return EventItemId;
        }

        public void setEventItemId(Integer eventItemId) {
            EventItemId = eventItemId;
        }

        public String getEventName() {
            return EventName;
        }

        public void setEventName(String eventName) {
            EventName = eventName;
        }
    }
    public class VideoWithTime {

        @SerializedName("VideoUrl")
        @Expose
        private String videoUrl;
        @SerializedName("Time")
        @Expose
        private String time;

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

    }
}