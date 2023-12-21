package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SwapAction implements Serializable {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("DueDate")
    @Expose
    private String dueDate;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("reminder_till_date")
    @Expose
    private String reminderTillDate;
    @SerializedName("last_reminder_inserted_date")
    @Expose
    private String lastReminderInsertedDate;
    @SerializedName("GoalId")
    @Expose
    private Integer goalId;
    @SerializedName("FrequencyId")
    @Expose
    private Integer frequencyId;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("HasReminder")
    @Expose
    private Boolean hasReminder;
    @SerializedName("Picture")
    @Expose
    private String picture;
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
    @SerializedName("ActionTime1")
    @Expose
    private Integer actionTime1;
    @SerializedName("ActionTime2")
    @Expose
    private Integer actionTime2;
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
    @SerializedName("MonthReminder")
    @Expose
    private Integer monthReminder;
    @SerializedName("IsCreatedUpdated")
    @Expose
    private Boolean isCreatedUpdated;
    @SerializedName("Status")
    @Expose
    private Boolean status;
    @SerializedName("LastReminderStatus")
    @Expose
    private Boolean lastReminderStatus;
    @SerializedName("GoalIdTemp")
    @Expose
    private Integer goalIdTemp;
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
   /* @SerializedName("GoalDetails")
    @Expose
    private Object goalDetails;*/
   /* @SerializedName("goal_mindset_reminder_master_Details")
    @Expose
    private List<Object> goalMindsetReminderMasterDetails = null;*/
    /*@SerializedName("UserDetail")
    @Expose
    private Object userDetail;
    @SerializedName("Position")
    @Expose
    private Object position;*/
    /*@SerializedName("UserActionId")
    @Expose
    private Object userActionId;
    @SerializedName("IsPersonalChallenge")
    @Expose
    private Object isPersonalChallenge;
    @SerializedName("IsSquadCreated")
    @Expose
    private Object isSquadCreated;
    @SerializedName("IsGroupChallenge")
    @Expose
    private Object isGroupChallenge;
    @SerializedName("EndDate")
    @Expose
    private Object endDate;*/
    @SerializedName("Participants")
    @Expose
    private Integer participants;
   /* @SerializedName("NotificationMsg")
    @Expose
    private Object notificationMsg;*/
    @SerializedName("TotalCompleted")
    @Expose
    private Integer totalCompleted;
    /*@SerializedName("Buddy_Privacy")
    @Expose
    private Object buddyPrivacy;*/
    @SerializedName("TaskFrequencyTypeId")
    @Expose
    private Integer taskFrequencyTypeId;
    @SerializedName("IsSundayTask")
    @Expose
    private Boolean isSundayTask;
    @SerializedName("IsMondayTask")
    @Expose
    private Boolean isMondayTask;
    @SerializedName("IsTuesdayTask")
    @Expose
    private Boolean isTuesdayTask;
    @SerializedName("IsWednesdayTask")
    @Expose
    private Boolean isWednesdayTask;
    @SerializedName("IsThursdayTask")
    @Expose
    private Boolean isThursdayTask;
    @SerializedName("IsFridayTask")
    @Expose
    private Boolean isFridayTask;
    @SerializedName("IsSaturdayTask")
    @Expose
    private Boolean isSaturdayTask;
    @SerializedName("IsJanuaryTask")
    @Expose
    private Boolean isJanuaryTask;
    @SerializedName("IsFebruaryTask")
    @Expose
    private Boolean isFebruaryTask;
    @SerializedName("IsMarchTask")
    @Expose
    private Boolean isMarchTask;
    @SerializedName("IsAprilTask")
    @Expose
    private Boolean isAprilTask;
    @SerializedName("IsMayTask")
    @Expose
    private Boolean isMayTask;
    @SerializedName("IsJuneTask")
    @Expose
    private Boolean isJuneTask;
    @SerializedName("IsJulyTask")
    @Expose
    private Boolean isJulyTask;
    @SerializedName("IsAugustTask")
    @Expose
    private Boolean isAugustTask;
    @SerializedName("IsSeptemberTask")
    @Expose
    private Boolean isSeptemberTask;
    @SerializedName("IsOctoberTask")
    @Expose
    private Boolean isOctoberTask;
    @SerializedName("IsNovemberTask")
    @Expose
    private Boolean isNovemberTask;
    @SerializedName("IsDecemberTask")
    @Expose
    private Boolean isDecemberTask;
    @SerializedName("TaskReminderAt1")
    @Expose
    private Integer taskReminderAt1;
    @SerializedName("TaskReminderAt2")
    @Expose
    private Integer taskReminderAt2;
    @SerializedName("TaskMonthReminder")
    @Expose
    private Integer taskMonthReminder;
    @SerializedName("CurrentDayTask")
    @Expose
    private CurrentDayTask currentDayTask;
    @SerializedName("CurrentDayTask2")
    @Expose
    private CurrentDayTask currentDayTask2;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("OverallPerformance")
    @Expose
    private Double overallPerformance;
    @SerializedName("TotalDone")
    @Expose
    private Double totalDone;



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

    public Integer getGoalId() {
        return goalId;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
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

    public Boolean getHasReminder() {
        return hasReminder;
    }

    public void setHasReminder(Boolean hasReminder) {
        this.hasReminder = hasReminder;
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

    public Integer getActionTime1() {
        return actionTime1;
    }

    public void setActionTime1(Integer actionTime1) {
        this.actionTime1 = actionTime1;
    }

    public Integer getActionTime2() {
        return actionTime2;
    }

    public void setActionTime2(Integer actionTime2) {
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

    public Integer getMonthReminder() {
        return monthReminder;
    }

    public void setMonthReminder(Integer monthReminder) {
        this.monthReminder = monthReminder;
    }

    public Boolean getIsCreatedUpdated() {
        return isCreatedUpdated;
    }

    public void setIsCreatedUpdated(Boolean isCreatedUpdated) {
        this.isCreatedUpdated = isCreatedUpdated;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getLastReminderStatus() {
        return lastReminderStatus;
    }

    public void setLastReminderStatus(Boolean lastReminderStatus) {
        this.lastReminderStatus = lastReminderStatus;
    }

    public Integer getGoalIdTemp() {
        return goalIdTemp;
    }

    public void setGoalIdTemp(Integer goalIdTemp) {
        this.goalIdTemp = goalIdTemp;
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
/*
    public Object getGoalDetails() {
        return goalDetails;
    }

    public void setGoalDetails(Object goalDetails) {
        this.goalDetails = goalDetails;
    }*/

   /* public List<Object> getGoalMindsetReminderMasterDetails() {
        return goalMindsetReminderMasterDetails;
    }

    public void setGoalMindsetReminderMasterDetails(List<Object> goalMindsetReminderMasterDetails) {
        this.goalMindsetReminderMasterDetails = goalMindsetReminderMasterDetails;
    }*/

  /*  public Object getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Object userDetail) {
        this.userDetail = userDetail;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getUserActionId() {
        return userActionId;
    }

    public void setUserActionId(Object userActionId) {
        this.userActionId = userActionId;
    }

    public Object getIsPersonalChallenge() {
        return isPersonalChallenge;
    }

    public void setIsPersonalChallenge(Object isPersonalChallenge) {
        this.isPersonalChallenge = isPersonalChallenge;
    }

    public Object getIsSquadCreated() {
        return isSquadCreated;
    }

    public void setIsSquadCreated(Object isSquadCreated) {
        this.isSquadCreated = isSquadCreated;
    }

    public Object getIsGroupChallenge() {
        return isGroupChallenge;
    }

    public void setIsGroupChallenge(Object isGroupChallenge) {
        this.isGroupChallenge = isGroupChallenge;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }*/

    public Integer getParticipants() {
        return participants;
    }

    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

   /* public Object getNotificationMsg() {
        return notificationMsg;
    }

    public void setNotificationMsg(Object notificationMsg) {
        this.notificationMsg = notificationMsg;
    }*/

    public Integer getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(Integer totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    /*public Object getBuddyPrivacy() {
        return buddyPrivacy;
    }

    public void setBuddyPrivacy(Object buddyPrivacy) {
        this.buddyPrivacy = buddyPrivacy;
    }*/

    public Integer getTaskFrequencyTypeId() {
        return taskFrequencyTypeId;
    }

    public void setTaskFrequencyTypeId(Integer taskFrequencyTypeId) {
        this.taskFrequencyTypeId = taskFrequencyTypeId;
    }

    public Boolean getIsSundayTask() {
        return isSundayTask;
    }

    public void setIsSundayTask(Boolean isSundayTask) {
        this.isSundayTask = isSundayTask;
    }

    public Boolean getIsMondayTask() {
        return isMondayTask;
    }

    public void setIsMondayTask(Boolean isMondayTask) {
        this.isMondayTask = isMondayTask;
    }

    public Boolean getIsTuesdayTask() {
        return isTuesdayTask;
    }

    public void setIsTuesdayTask(Boolean isTuesdayTask) {
        this.isTuesdayTask = isTuesdayTask;
    }

    public Boolean getIsWednesdayTask() {
        return isWednesdayTask;
    }

    public void setIsWednesdayTask(Boolean isWednesdayTask) {
        this.isWednesdayTask = isWednesdayTask;
    }

    public Boolean getIsThursdayTask() {
        return isThursdayTask;
    }

    public void setIsThursdayTask(Boolean isThursdayTask) {
        this.isThursdayTask = isThursdayTask;
    }

    public Boolean getIsFridayTask() {
        return isFridayTask;
    }

    public void setIsFridayTask(Boolean isFridayTask) {
        this.isFridayTask = isFridayTask;
    }

    public Boolean getIsSaturdayTask() {
        return isSaturdayTask;
    }

    public void setIsSaturdayTask(Boolean isSaturdayTask) {
        this.isSaturdayTask = isSaturdayTask;
    }

    public Boolean getIsJanuaryTask() {
        return isJanuaryTask;
    }

    public void setIsJanuaryTask(Boolean isJanuaryTask) {
        this.isJanuaryTask = isJanuaryTask;
    }

    public Boolean getIsFebruaryTask() {
        return isFebruaryTask;
    }

    public void setIsFebruaryTask(Boolean isFebruaryTask) {
        this.isFebruaryTask = isFebruaryTask;
    }

    public Boolean getIsMarchTask() {
        return isMarchTask;
    }

    public void setIsMarchTask(Boolean isMarchTask) {
        this.isMarchTask = isMarchTask;
    }

    public Boolean getIsAprilTask() {
        return isAprilTask;
    }

    public void setIsAprilTask(Boolean isAprilTask) {
        this.isAprilTask = isAprilTask;
    }

    public Boolean getIsMayTask() {
        return isMayTask;
    }

    public void setIsMayTask(Boolean isMayTask) {
        this.isMayTask = isMayTask;
    }

    public Boolean getIsJuneTask() {
        return isJuneTask;
    }

    public void setIsJuneTask(Boolean isJuneTask) {
        this.isJuneTask = isJuneTask;
    }

    public Boolean getIsJulyTask() {
        return isJulyTask;
    }

    public void setIsJulyTask(Boolean isJulyTask) {
        this.isJulyTask = isJulyTask;
    }

    public Boolean getIsAugustTask() {
        return isAugustTask;
    }

    public void setIsAugustTask(Boolean isAugustTask) {
        this.isAugustTask = isAugustTask;
    }

    public Boolean getIsSeptemberTask() {
        return isSeptemberTask;
    }

    public void setIsSeptemberTask(Boolean isSeptemberTask) {
        this.isSeptemberTask = isSeptemberTask;
    }

    public Boolean getIsOctoberTask() {
        return isOctoberTask;
    }

    public void setIsOctoberTask(Boolean isOctoberTask) {
        this.isOctoberTask = isOctoberTask;
    }

    public Boolean getIsNovemberTask() {
        return isNovemberTask;
    }

    public void setIsNovemberTask(Boolean isNovemberTask) {
        this.isNovemberTask = isNovemberTask;
    }

    public Boolean getIsDecemberTask() {
        return isDecemberTask;
    }

    public void setIsDecemberTask(Boolean isDecemberTask) {
        this.isDecemberTask = isDecemberTask;
    }

    public Integer getTaskReminderAt1() {
        return taskReminderAt1;
    }

    public void setTaskReminderAt1(Integer taskReminderAt1) {
        this.taskReminderAt1 = taskReminderAt1;
    }

    public Integer getTaskReminderAt2() {
        return taskReminderAt2;
    }

    public void setTaskReminderAt2(Integer taskReminderAt2) {
        this.taskReminderAt2 = taskReminderAt2;
    }

    public Integer getTaskMonthReminder() {
        return taskMonthReminder;
    }

    public void setTaskMonthReminder(Integer taskMonthReminder) {
        this.taskMonthReminder = taskMonthReminder;
    }

    public CurrentDayTask getCurrentDayTask() {
        return currentDayTask;
    }

    public void setCurrentDayTask(CurrentDayTask currentDayTask) {
        this.currentDayTask = currentDayTask;
    }

    public CurrentDayTask getCurrentDayTask2() {
        return currentDayTask2;
    }

    public void setCurrentDayTask2(CurrentDayTask currentDayTask2) {
        this.currentDayTask2 = currentDayTask2;
    }

    public Integer getDuration() {
        return duration;
    }

    public SwapAction setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public Double getOverallPerformance() {
        return overallPerformance;
    }

    public SwapAction setOverallPerformance(Double overallPerformance) {
        this.overallPerformance = overallPerformance;
        return this;
    }

    public Double getTotalDone() {
        return totalDone;
    }

    public SwapAction setTotalDone(Double totalDone) {
        this.totalDone = totalDone;
        return this;
    }

}
