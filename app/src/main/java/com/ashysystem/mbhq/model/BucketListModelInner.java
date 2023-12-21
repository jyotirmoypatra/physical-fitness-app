package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 3/20/17.
 */

public class BucketListModelInner {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("reminder_till_date")
    @Expose
    private Object reminderTillDate;
    @SerializedName("last_reminder_inserted_date")
    @Expose
    private String lastReminderInsertedDate;
    @SerializedName("FrequencyId")
    @Expose
    private Integer frequencyId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("CompletionDate")
    @Expose
    private String completionDate;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("ActionTime1")
    @Expose
    private Object actionTime1;
    @SerializedName("ActionTime2")
    @Expose
    private Object actionTime2;
    @SerializedName("Sunday")
    @Expose
    private Boolean sunday;
    @SerializedName("Monday")
    @Expose
    private Boolean monday;
    @SerializedName("Tuesday")
    @Expose
    private Boolean tuesday;
    @SerializedName("Wednesday")
    @Expose
    private Boolean wednesday;
    @SerializedName("Thursday")
    @Expose
    private Boolean thursday;
    @SerializedName("Friday")
    @Expose
    private Boolean friday;
    @SerializedName("Saturday")
    @Expose
    private Boolean saturday;
    @SerializedName("ReminderAt1")
    @Expose
    private Integer reminderAt1;
    @SerializedName("ReminderAt2")
    @Expose
    private Integer reminderAt2;
    @SerializedName("ReminderOption")
    @Expose
    private Integer reminderOption;
    @SerializedName("Email")
    @Expose
    private Boolean email;
    @SerializedName("PushNotification")
    @Expose
    private Boolean pushNotification;
    @SerializedName("CreatedAt")
    @Expose
    private String createdAt;
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("Status")
    @Expose
    private Boolean status;

    public Integer getBucketStatus() {
        return BucketStatus;
    }

    public void setBucketStatus(Integer bucketStatus) {
        BucketStatus = bucketStatus;
    }

    @SerializedName("BucketStatus")
    @Expose
    private Integer BucketStatus;
    @SerializedName("Picture")
    @Expose
    private String picture;

    public String getPictureDevicePath() {
        return PictureDevicePath;
    }

    public void setPictureDevicePath(String pictureDevicePath) {
        PictureDevicePath = pictureDevicePath;
    }

    @SerializedName("PictureDevicePath")
    @Expose
    private String PictureDevicePath;
    @SerializedName("UploadPictureImgFileName")
    @Expose
    private String uploadPictureImgFileName;
    @SerializedName("UploadPictureImg")
    @Expose
    private String uploadPictureImg;
    @SerializedName("UploadPictureImgBase64")
    @Expose
    private String uploadPictureImgBase64;
    @SerializedName("Song")
    @Expose
    private String song;
    @SerializedName("MonthReminder")
    @Expose
    private Integer monthReminder;
    @SerializedName("IsCreatedUpdated")
    @Expose
    private Object isCreatedUpdated;
    @SerializedName("January")
    @Expose
    private Boolean january;
    @SerializedName("February")
    @Expose
    private Boolean february;
    @SerializedName("March")
    @Expose
    private Boolean march;
    @SerializedName("April")
    @Expose
    private Boolean april;
    @SerializedName("May")
    @Expose
    private Boolean may;
    @SerializedName("June")
    @Expose
    private Boolean june;
    @SerializedName("July")
    @Expose
    private Boolean july;
    @SerializedName("August")
    @Expose
    private Boolean august;
    @SerializedName("September")
    @Expose
    private Boolean september;
    @SerializedName("October")
    @Expose
    private Boolean october;
    @SerializedName("November")
    @Expose
    private Boolean november;
    @SerializedName("December")
    @Expose
    private Boolean december;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getReminderTillDate() {
        return reminderTillDate;
    }

    public void setReminderTillDate(Object reminderTillDate) {
        this.reminderTillDate = reminderTillDate;
    }

    public String getLastReminderInsertedDate() {
        return lastReminderInsertedDate;
    }

    public void setLastReminderInsertedDate(String lastReminderInsertedDate) {
        this.lastReminderInsertedDate = lastReminderInsertedDate;
    }

    public Integer getFrequencyId() {
        return frequencyId;
    }

    public void setFrequencyId(Integer frequencyId) {
        this.frequencyId = frequencyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Object getActionTime1() {
        return actionTime1;
    }

    public void setActionTime1(Object actionTime1) {
        this.actionTime1 = actionTime1;
    }

    public Object getActionTime2() {
        return actionTime2;
    }

    public void setActionTime2(Object actionTime2) {
        this.actionTime2 = actionTime2;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    public Integer getReminderAt1() {
        return reminderAt1;
    }

    public void setReminderAt1(Integer reminderAt1) {
        this.reminderAt1 = reminderAt1;
    }

    public Integer getReminderAt2() {
        return reminderAt2;
    }

    public void setReminderAt2(Integer reminderAt2) {
        this.reminderAt2 = reminderAt2;
    }

    public Integer getReminderOption() {
        return reminderOption;
    }

    public void setReminderOption(Integer reminderOption) {
        this.reminderOption = reminderOption;
    }

    public Boolean getEmail() {
        return email;
    }

    public void setEmail(Boolean email) {
        this.email = email;
    }

    public Boolean getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUploadPictureImgFileName() {
        return uploadPictureImgFileName;
    }

    public void setUploadPictureImgFileName(String uploadPictureImgFileName) {
        this.uploadPictureImgFileName = uploadPictureImgFileName;
    }

    public String getUploadPictureImg() {
        return uploadPictureImg;
    }

    public void setUploadPictureImg(String uploadPictureImg) {
        this.uploadPictureImg = uploadPictureImg;
    }

    public String getUploadPictureImgBase64() {
        return uploadPictureImgBase64;
    }

    public void setUploadPictureImgBase64(String uploadPictureImgBase64) {
        this.uploadPictureImgBase64 = uploadPictureImgBase64;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public Integer getMonthReminder() {
        return monthReminder;
    }

    public void setMonthReminder(Integer monthReminder) {
        this.monthReminder = monthReminder;
    }

    public Object getIsCreatedUpdated() {
        return isCreatedUpdated;
    }

    public void setIsCreatedUpdated(Object isCreatedUpdated) {
        this.isCreatedUpdated = isCreatedUpdated;
    }

    public Boolean getJanuary() {
        return january;
    }

    public void setJanuary(Boolean january) {
        this.january = january;
    }

    public Boolean getFebruary() {
        return february;
    }

    public void setFebruary(Boolean february) {
        this.february = february;
    }

    public Boolean getMarch() {
        return march;
    }

    public void setMarch(Boolean march) {
        this.march = march;
    }

    public Boolean getApril() {
        return april;
    }

    public void setApril(Boolean april) {
        this.april = april;
    }

    public Boolean getMay() {
        return may;
    }

    public void setMay(Boolean may) {
        this.may = may;
    }

    public Boolean getJune() {
        return june;
    }

    public void setJune(Boolean june) {
        this.june = june;
    }

    public Boolean getJuly() {
        return july;
    }

    public void setJuly(Boolean july) {
        this.july = july;
    }

    public Boolean getAugust() {
        return august;
    }

    public void setAugust(Boolean august) {
        this.august = august;
    }

    public Boolean getSeptember() {
        return september;
    }

    public void setSeptember(Boolean september) {
        this.september = september;
    }

    public Boolean getOctober() {
        return october;
    }

    public void setOctober(Boolean october) {
        this.october = october;
    }

    public Boolean getNovember() {
        return november;
    }

    public void setNovember(Boolean november) {
        this.november = november;
    }

    public Boolean getDecember() {
        return december;
    }

    public void setDecember(Boolean december) {
        this.december = december;
    }

}