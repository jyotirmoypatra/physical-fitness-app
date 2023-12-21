package com.ashysystem.mbhq.model.response;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;

/**
 * Created by android-krishnendu on 3/16/17.
 */
@Entity
public class MyAchievementsListInnerModel {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "timestamp")
    private String timeStamp;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @SerializedName("Id")
    @Expose
    private Integer id;

    @ColumnInfo(name = "DueDate")
    @SerializedName("DueDate")
    @Expose
    private String dueDate;

    @ColumnInfo(name = "reminder_till_date")
    @SerializedName("reminder_till_date")
    @Expose
    private String reminderTillDate;

    @ColumnInfo(name = "last_reminder_inserted_date")
    @SerializedName("last_reminder_inserted_date")
    @Expose
    private String lastReminderInsertedDate;

    @ColumnInfo(name = "Prompt")
    @SerializedName("Prompt")
    @Expose
    private String prompt;

    @ColumnInfo(name = "IsPromptOfDay")
    @SerializedName("IsPromptOfDay")
    @Expose
    private Boolean isPromptOfDay;

    @ColumnInfo(name = "Achievement")
    @SerializedName("Achievement")
    @Expose
    private String achievement;

    @ColumnInfo(name = "Notes")
    @SerializedName("Notes")
    @Expose
    private String notes;
    String UserEqFolderId;

    public String getUserEqFolderId() {
        return UserEqFolderId;
    }

    public void setUserEqFolderId(String userEqFolderId) {
        UserEqFolderId = userEqFolderId;
    }

    @ColumnInfo(name = "UserFolderId")
    @SerializedName("UserFolderId")
    @Expose
    private Integer UserFolderId=0;

    @ColumnInfo(name = "FrequencyId")
    @SerializedName("FrequencyId")
    @Expose
    private Integer frequencyId;

    @ColumnInfo(name = "CategoryId")
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;

    @ColumnInfo(name = "Picture")
    @SerializedName("Picture")
    @Expose
    private String picture;

    @ColumnInfo(name = "PictureDevicePath")
    @SerializedName("PictureDevicePath")
    @Expose
    private String pictureDevicePath;


    @ColumnInfo(name = "HasImage")
    @SerializedName("HasImage")
    @Expose
    private Boolean HasImage;


    @ColumnInfo(name = "UploadPictureImgFileName")
    @SerializedName("UploadPictureImgFileName")
    @Expose
    private String uploadPictureImgFileName;

    @ColumnInfo(name = "UploadPictureImg")
    @SerializedName("UploadPictureImg")
    @Expose
    private String uploadPictureImg;

    @ColumnInfo(name = "UploadPictureImgBase64")
    @SerializedName("UploadPictureImgBase64")
    @Expose
    private String uploadPictureImgBase64;

    @ColumnInfo(name = "Song")
    @SerializedName("Song")
    @Expose
    private String song;


    @ColumnInfo(name = "ReminderAt1")
    @SerializedName("ReminderAt1")
    @Expose
    private Integer reminderAt1;

    @ColumnInfo(name = "ReminderAt2")
    @SerializedName("ReminderAt2")
    @Expose
    private Integer reminderAt2;

    @ColumnInfo(name = "ReminderAt1Int")
    @SerializedName("ReminderAt1Int")
    @Expose
    private Integer reminderAt1Int;


    @ColumnInfo(name = "ReminderAt2Int")
    @SerializedName("ReminderAt2Int")
    @Expose
    private Integer reminderAt2Int;


    @ColumnInfo(name = "ReminderOption")
    @SerializedName("ReminderOption")
    @Expose
    private Integer reminderOption;


    @ColumnInfo(name = "Email")
    @SerializedName("Email")
    @Expose
    private Boolean email;


    @ColumnInfo(name = "PushNotification")
    @SerializedName("PushNotification")
    @Expose
    private Boolean pushNotification;


    @ColumnInfo(name = "Sunday")
    @SerializedName("Sunday")
    @Expose
    private Boolean sunday;

    @ColumnInfo(name = "Monday")
    @SerializedName("Monday")
    @Expose
    private Boolean monday;

    @ColumnInfo(name = "Tuesday")
    @SerializedName("Tuesday")
    @Expose
    private Boolean tuesday;

    @ColumnInfo(name = "Wednesday")
    @SerializedName("Wednesday")
    @Expose
    private Boolean wednesday;

    @ColumnInfo(name = "Thursday")
    @SerializedName("Thursday")
    @Expose
    private Boolean thursday;

    @ColumnInfo(name = "Friday")
    @SerializedName("Friday")
    @Expose
    private Boolean friday;

    @ColumnInfo(name = "Saturday")
    @SerializedName("Saturday")
    @Expose
    private Boolean saturday;

    @ColumnInfo(name = "CreatedAt")
    @SerializedName("CreatedAt")
    @Expose
    private String createdAt;

    @ColumnInfo(name = "CreatedBy")
    @SerializedName("CreatedBy")
    @Expose
    private Integer createdBy;

    @ColumnInfo(name = "IsDeleted")
    @SerializedName("IsDeleted")
    @Expose
    private Boolean isDeleted;

    @ColumnInfo(name = "Status")
    @SerializedName("Status")
    @Expose
    private Boolean status;

    @ColumnInfo(name = "MonthReminder")
    @SerializedName("MonthReminder")
    @Expose
    private Integer monthReminder;

   /* @ColumnInfo(name = "IsCreatedUpdated")
    @SerializedName("IsCreatedUpdated")
    @Expose
    private Object isCreatedUpdated;*/

    @ColumnInfo(name = "January")
    @SerializedName("January")
    @Expose
    private Boolean january;

    @ColumnInfo(name = "February")
    @SerializedName("February")
    @Expose
    private Boolean february;

    @ColumnInfo(name = "March")
    @SerializedName("March")
    @Expose
    private Boolean march;

    @ColumnInfo(name = "April")
    @SerializedName("April")
    @Expose
    private Boolean april;

    @ColumnInfo(name = "May")
    @SerializedName("May")
    @Expose
    private Boolean may;

    @ColumnInfo(name = "June")
    @SerializedName("June")
    @Expose
    private Boolean june;

    @ColumnInfo(name = "July")
    @SerializedName("July")
    @Expose
    private Boolean july;

    @ColumnInfo(name = "August")
    @SerializedName("August")
    @Expose
    private Boolean august;

    @ColumnInfo(name = "September")
    @SerializedName("September")
    @Expose
    private Boolean september;

    @ColumnInfo(name = "October")
    @SerializedName("October")
    @Expose
    private Boolean october;

    @ColumnInfo(name = "November")
    @SerializedName("November")
    @Expose
    private Boolean november;

    @ColumnInfo(name = "December")
    @SerializedName("December")
    @Expose
    private Boolean december;

    private transient String dateValue = "";
    private transient File file=null;
    private transient Bitmap bitmap=null;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Boolean getHasImage() {
        return HasImage;
    }

    public void setHasImage(Boolean hasImage) {
        HasImage = hasImage;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;

    }

    public Integer getUserFolderId() {
        return UserFolderId;
    }

    public void setUserFolderId(Integer userFolderId) {
        UserFolderId = userFolderId;
    }

    public Boolean getPromptOfDay() {
        return isPromptOfDay;
    }

    public void setPromptOfDay(Boolean promptOfDay) {
        isPromptOfDay = promptOfDay;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getReminderTillDate() {
        return reminderTillDate;
    }

    public void setReminderTillDate(String reminderTillDate) {
        this.reminderTillDate = reminderTillDate;
    }

    public String getLastReminderInsertedDate() {
        return lastReminderInsertedDate;
    }

    public void setLastReminderInsertedDate(String lastReminderInsertedDate) {
        this.lastReminderInsertedDate = lastReminderInsertedDate;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public Integer getMonthReminder() {
        return monthReminder;
    }

    public void setMonthReminder(Integer monthReminder) {
        this.monthReminder = monthReminder;
    }

/*
    public Object getIsCreatedUpdated() {
        return isCreatedUpdated;
    }

    public void setIsCreatedUpdated(Object isCreatedUpdated) {
        this.isCreatedUpdated = isCreatedUpdated;
    }
*/

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

   /* public Integer getReminderAt1Int() {
        return reminderAt1Int;
    }

    public MyAchievementsListInnerModel setReminderAt1Int(Integer reminderAt1Int) {
        this.reminderAt1Int = reminderAt1Int;
        return this;
    }

    public Integer getReminderAt2Int() {
        return reminderAt2Int;
    }

    public MyAchievementsListInnerModel setReminderAt2Int(Integer reminderAt2Int) {
        this.reminderAt2Int = reminderAt2Int;
        return this;
    }*/

    public String getPictureDevicePath() {
        return pictureDevicePath;
    }

    public void setPictureDevicePath(String pictureDevicePath) {
        this.pictureDevicePath = pictureDevicePath;
    }

    public Integer getReminderAt1Int() {
        return reminderAt1Int;
    }

    public void setReminderAt1Int(Integer reminderAt1Int) {
        this.reminderAt1Int = reminderAt1Int;
    }

    public Integer getReminderAt2Int() {
        return reminderAt2Int;
    }

    public void setReminderAt2Int(Integer reminderAt2Int) {
        this.reminderAt2Int = reminderAt2Int;
    }

   /* public String getPictureDevicePath() {
        return pictureDevicePath;
    }

    public MyAchievementsListInnerModel setPictureDevicePath(String pictureDevicePath) {
        this.pictureDevicePath = pictureDevicePath;
        return this;
    }*/


}
