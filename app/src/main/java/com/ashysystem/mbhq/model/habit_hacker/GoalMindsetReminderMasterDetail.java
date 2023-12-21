package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoalMindsetReminderMasterDetail {

    @SerializedName("goal_mindset_reminder_master_id")
    @Expose
    private Integer goalMindsetReminderMasterId;
    @SerializedName("goal_id")
    @Expose
    private Integer goalId;
    @SerializedName("action_id")
    @Expose
    private Integer actionId;
    @SerializedName("vision_board_id")
    @Expose
    private Integer visionBoardId;
    @SerializedName("bucket_list_id")
    @Expose
    private Integer bucketListId;
    @SerializedName("gratitude_list_id")
    @Expose
    private Integer gratitudeListId;
    @SerializedName("affirmation_id")
    @Expose
    private Integer affirmationId;
    @SerializedName("reversebucket_list_id")
    @Expose
    private Integer reversebucketListId;
    @SerializedName("user_vitamin_id")
    @Expose
    private Integer userVitaminId;
    @SerializedName("reminder_datetime")
    @Expose
    private String reminderDatetime;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_email")
    @Expose
    private Boolean isEmail;
    @SerializedName("is_push_notification")
    @Expose
    private Boolean isPushNotification;
    @SerializedName("is_email_send")
    @Expose
    private Boolean isEmailSend;
    @SerializedName("is_push_notificationSend")
    @Expose
    private Boolean isPushNotificationSend;
    @SerializedName("guid_token")
    @Expose
    private Object guidToken;
    @SerializedName("is_action")
    @Expose
    private Boolean isAction;
    @SerializedName("snooze_add_time")
    @Expose
    private Object snoozeAddTime;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("IsPersonalChallengeReminder")
    @Expose
    private Boolean isPersonalChallengeReminder;
    @SerializedName("userDetail")
    @Expose
    private Object userDetail;
    @SerializedName("goal")
    @Expose
    private Object goal;
    @SerializedName("goal_actions")
    @Expose
    private Object goalActions;
    @SerializedName("goal_bucket_list")
    @Expose
    private Object goalBucketList;
    @SerializedName("goal_gratitude_list")
    @Expose
    private Object goalGratitudeList;
    @SerializedName("goal_reverse_bucket_list")
    @Expose
    private Object goalReverseBucketList;
    @SerializedName("goal_vision_board")
    @Expose
    private Object goalVisionBoard;
    @SerializedName("goal_affirmation")
    @Expose
    private Object goalAffirmation;
    @SerializedName("user_vitamin")
    @Expose
    private Object userVitamin;
    @SerializedName("reminder_datetime_string")
    @Expose
    private String reminderDatetimeString;
    @SerializedName("reminder_datetime_USstring")
    @Expose
    private String reminderDatetimeUSstring;
    @SerializedName("HabitId")
    @Expose
    private Integer habitId;
    @SerializedName("CheckDone")
    @Expose
    private Object checkDone;

    public Integer getGoalMindsetReminderMasterId() {
        return goalMindsetReminderMasterId;
    }

    public void setGoalMindsetReminderMasterId(Integer goalMindsetReminderMasterId) {
        this.goalMindsetReminderMasterId = goalMindsetReminderMasterId;
    }

    public Integer getGoalId() {
        return goalId;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getVisionBoardId() {
        return visionBoardId;
    }

    public void setVisionBoardId(Integer visionBoardId) {
        this.visionBoardId = visionBoardId;
    }

    public Integer getBucketListId() {
        return bucketListId;
    }

    public void setBucketListId(Integer bucketListId) {
        this.bucketListId = bucketListId;
    }

    public Integer getGratitudeListId() {
        return gratitudeListId;
    }

    public void setGratitudeListId(Integer gratitudeListId) {
        this.gratitudeListId = gratitudeListId;
    }

    public Integer getAffirmationId() {
        return affirmationId;
    }

    public void setAffirmationId(Integer affirmationId) {
        this.affirmationId = affirmationId;
    }

    public Integer getReversebucketListId() {
        return reversebucketListId;
    }

    public void setReversebucketListId(Integer reversebucketListId) {
        this.reversebucketListId = reversebucketListId;
    }

    public Integer getUserVitaminId() {
        return userVitaminId;
    }

    public void setUserVitaminId(Integer userVitaminId) {
        this.userVitaminId = userVitaminId;
    }

    public String getReminderDatetime() {
        return reminderDatetime;
    }

    public void setReminderDatetime(String reminderDatetime) {
        this.reminderDatetime = reminderDatetime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(Boolean isEmail) {
        this.isEmail = isEmail;
    }

    public Boolean getIsPushNotification() {
        return isPushNotification;
    }

    public void setIsPushNotification(Boolean isPushNotification) {
        this.isPushNotification = isPushNotification;
    }

    public Boolean getIsEmailSend() {
        return isEmailSend;
    }

    public void setIsEmailSend(Boolean isEmailSend) {
        this.isEmailSend = isEmailSend;
    }

    public Boolean getIsPushNotificationSend() {
        return isPushNotificationSend;
    }

    public void setIsPushNotificationSend(Boolean isPushNotificationSend) {
        this.isPushNotificationSend = isPushNotificationSend;
    }

    public Object getGuidToken() {
        return guidToken;
    }

    public void setGuidToken(Object guidToken) {
        this.guidToken = guidToken;
    }

    public Boolean getIsAction() {
        return isAction;
    }

    public void setIsAction(Boolean isAction) {
        this.isAction = isAction;
    }

    public Object getSnoozeAddTime() {
        return snoozeAddTime;
    }

    public void setSnoozeAddTime(Object snoozeAddTime) {
        this.snoozeAddTime = snoozeAddTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsPersonalChallengeReminder() {
        return isPersonalChallengeReminder;
    }

    public void setIsPersonalChallengeReminder(Boolean isPersonalChallengeReminder) {
        this.isPersonalChallengeReminder = isPersonalChallengeReminder;
    }

    public Object getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(Object userDetail) {
        this.userDetail = userDetail;
    }

    public Object getGoal() {
        return goal;
    }

    public void setGoal(Object goal) {
        this.goal = goal;
    }

    public Object getGoalActions() {
        return goalActions;
    }

    public void setGoalActions(Object goalActions) {
        this.goalActions = goalActions;
    }

    public Object getGoalBucketList() {
        return goalBucketList;
    }

    public void setGoalBucketList(Object goalBucketList) {
        this.goalBucketList = goalBucketList;
    }

    public Object getGoalGratitudeList() {
        return goalGratitudeList;
    }

    public void setGoalGratitudeList(Object goalGratitudeList) {
        this.goalGratitudeList = goalGratitudeList;
    }

    public Object getGoalReverseBucketList() {
        return goalReverseBucketList;
    }

    public void setGoalReverseBucketList(Object goalReverseBucketList) {
        this.goalReverseBucketList = goalReverseBucketList;
    }

    public Object getGoalVisionBoard() {
        return goalVisionBoard;
    }

    public void setGoalVisionBoard(Object goalVisionBoard) {
        this.goalVisionBoard = goalVisionBoard;
    }

    public Object getGoalAffirmation() {
        return goalAffirmation;
    }

    public void setGoalAffirmation(Object goalAffirmation) {
        this.goalAffirmation = goalAffirmation;
    }

    public Object getUserVitamin() {
        return userVitamin;
    }

    public void setUserVitamin(Object userVitamin) {
        this.userVitamin = userVitamin;
    }

    public String getReminderDatetimeString() {
        return reminderDatetimeString;
    }

    public void setReminderDatetimeString(String reminderDatetimeString) {
        this.reminderDatetimeString = reminderDatetimeString;
    }

    public String getReminderDatetimeUSstring() {
        return reminderDatetimeUSstring;
    }

    public void setReminderDatetimeUSstring(String reminderDatetimeUSstring) {
        this.reminderDatetimeUSstring = reminderDatetimeUSstring;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }

    public Object getCheckDone() {
        return checkDone;
    }

    public void setCheckDone(Object checkDone) {
        this.checkDone = checkDone;
    }

}
