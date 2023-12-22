package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourseDetailModel {

    @SerializedName("ArticleFeedDetail")
    @Expose
    private List<ArticleFeedDetail> articleFeedDetail = null;
    @SerializedName("SelectedWeekNumber")
    @Expose
    private Integer selectedWeekNumber;
    @SerializedName("CourseId")
    @Expose
    private Integer courseId;

    public Boolean getSeminarNotification() {
        return SeminarNotification;
    }

    public void setSeminarNotification(Boolean seminarNotification) {
        SeminarNotification = seminarNotification;
    }

    public Boolean getMessageNotification() {
        return MessageNotification;
    }

    public void setMessageNotification(Boolean messageNotification) {
        MessageNotification = messageNotification;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    @SerializedName("Status")
    @Expose
    private Integer Status;
    @SerializedName("SeminarNotification")
    @Expose
    private Boolean SeminarNotification;

    @SerializedName("MessageNotification")
    @Expose
    private Boolean MessageNotification;
    @SerializedName("RunBy")
    @Expose
    private String runBy;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<ArticleFeedDetail> getArticleFeedDetail() {
        return articleFeedDetail;
    }

    public void setArticleFeedDetail(List<ArticleFeedDetail> articleFeedDetail) {
        this.articleFeedDetail = articleFeedDetail;
    }

    public Integer getSelectedWeekNumber() {
        return selectedWeekNumber;
    }

    public void setSelectedWeekNumber(Integer selectedWeekNumber) {
        this.selectedWeekNumber = selectedWeekNumber;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getRunBy() {
        return runBy;
    }

    public void setRunBy(String runBy) {
        this.runBy = runBy;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
//////////////////////////////////////


    public class ArticleFeedDetail {

        @SerializedName("ArticleId")
        @Expose
        private Integer articleId;
        @SerializedName("DisplayOrder")
        @Expose
        private Integer displayOrder;
        @SerializedName("ShowOnDay")
        @Expose
        private Integer showOnDay;
        @SerializedName("ArticleTitle")
        @Expose
        private String articleTitle;
        @SerializedName("ArticleBrief")
        @Expose
        private String articleBrief;
        @SerializedName("VideoUrl")
        @Expose
        private Object videoUrl;
        @SerializedName("PublicUrl")
        @Expose
        private Object publicUrl;
        @SerializedName("WeekNumber")
        @Expose
        private Integer weekNumber;
        @SerializedName("AuthorName")
        @Expose
        private String authorName;
        @SerializedName("AuthorPhoto")
        @Expose
        private String authorPhoto;
        @SerializedName("RelatedTask")
        @Expose
        private RelatedTask relatedTask;
        @SerializedName("IsRead")
        @Expose
        private Boolean isRead;
        @SerializedName("Tags")
        @Expose
        private Object tags;
        @SerializedName("AuthorId")
        @Expose
        private Integer authorId;
        @SerializedName("SeminarTime")
        @Expose
        private String seminarTime;
        @SerializedName("ReleaseDate")
        @Expose
        private String releaseDate;
        @SerializedName("WeeklyTheme")
        @Expose
        private String WeeklyTheme;

        public Boolean getAvailable() {
            return IsAvailable;
        }

        public void setAvailable(Boolean available) {
            IsAvailable = available;
        }

        @SerializedName("IsAvailable")
        @Expose
        private Boolean IsAvailable;

        public Integer getArticleId() {
            return articleId;
        }

        public void setArticleId(Integer articleId) {
            this.articleId = articleId;
        }

        public Integer getDisplayOrder() {
            return displayOrder;
        }

        public void setDisplayOrder(Integer displayOrder) {
            this.displayOrder = displayOrder;
        }

        public Integer getShowOnDay() {
            return showOnDay;
        }

        public void setShowOnDay(Integer showOnDay) {
            this.showOnDay = showOnDay;
        }

        public String getArticleTitle() {
            return articleTitle;
        }

        public void setArticleTitle(String articleTitle) {
            this.articleTitle = articleTitle;
        }

        public String getArticleBrief() {
            return articleBrief;
        }

        public void setArticleBrief(String articleBrief) {
            this.articleBrief = articleBrief;
        }

        public Object getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(Object videoUrl) {
            this.videoUrl = videoUrl;
        }

        public Object getPublicUrl() {
            return publicUrl;
        }

        public void setPublicUrl(Object publicUrl) {
            this.publicUrl = publicUrl;
        }

        public Integer getWeekNumber() {
            return weekNumber;
        }

        public void setWeekNumber(Integer weekNumber) {
            this.weekNumber = weekNumber;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getAuthorPhoto() {
            return authorPhoto;
        }

        public void setAuthorPhoto(String authorPhoto) {
            this.authorPhoto = authorPhoto;
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

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public Integer getAuthorId() {
            return authorId;
        }

        public void setAuthorId(Integer authorId) {
            this.authorId = authorId;
        }

        public String getSeminarTime() {
            return seminarTime;
        }

        public void setSeminarTime(String seminarTime) {
            this.seminarTime = seminarTime;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getWeeklyTheme() {
            return WeeklyTheme;
        }

        public void setWeeklyTheme(String weeklyTheme) {
            WeeklyTheme = weeklyTheme;
        }

    }
    ///////////////////////////////////////



    ////////////////////////////////////

    public class RelatedTask {

        @SerializedName("TaskId")
        @Expose
        private Integer taskId;
        @SerializedName("TaskTitle")
        @Expose
        private String taskTitle;
        @SerializedName("Instructions")
        @Expose
        private List<String> instructions = null;
        @SerializedName("TaskText")
        @Expose
        private Object taskText;
        @SerializedName("IsDone")
        @Expose
        private Boolean isDone;

        public Integer getTaskId() {
            return taskId;
        }

        public void setTaskId(Integer taskId) {
            this.taskId = taskId;
        }

        public String getTaskTitle() {
            return taskTitle;
        }

        public void setTaskTitle(String taskTitle) {
            this.taskTitle = taskTitle;
        }

        public List<String> getInstructions() {
            return instructions;
        }

        public void setInstructions(List<String> instructions) {
            this.instructions = instructions;
        }

        public Object getTaskText() {
            return taskText;
        }

        public void setTaskText(Object taskText) {
            this.taskText = taskText;
        }

        public Boolean getIsDone() {
            return isDone;
        }

        public void setIsDone(Boolean isDone) {
            this.isDone = isDone;
        }

    }
}