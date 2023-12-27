package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 4/11/17.
 */

public class GetSquadWorkoutSessionModel {

    @SerializedName("SquadUserWorkoutSession")
    @Expose
    private SquadUserWorkoutSession squadUserWorkoutSession;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public SquadUserWorkoutSession getSquadUserWorkoutSession() {
        return squadUserWorkoutSession;
    }

    public void setSquadUserWorkoutSession(SquadUserWorkoutSession squadUserWorkoutSession) {
        this.squadUserWorkoutSession = squadUserWorkoutSession;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public class ExerciseSessionDetails {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Program")
        @Expose
        private String program;
        @SerializedName("SessionTitle")
        @Expose
        private String sessionTitle;
        @SerializedName("SessionCode")
        @Expose
        private String sessionCode;
        @SerializedName("SessionType")
        @Expose
        private Integer sessionType;
        @SerializedName("BodyArea")
        @Expose
        private Integer bodyArea;
        @SerializedName("WeekNumber")
        @Expose
        private Integer weekNumber;
        @SerializedName("MinFitness")
        @Expose
        private Integer minFitness;
        @SerializedName("Duration")
        @Expose
        private Integer duration;
        @SerializedName("EquipmentRequired")
        @Expose
        private Integer equipmentRequired;
        @SerializedName("ExerciseLocationId")
        @Expose
        private Integer exerciseLocationId;
        @SerializedName("SessionSrNumber")
        @Expose
        private Integer sessionSrNumber;
        @SerializedName("SessionCategory")
        @Expose
        private Integer sessionCategory;
        @SerializedName("SessionOverviewText")
        @Expose
        private String sessionOverviewText;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("ExcludeInAutoSession")
        @Expose
        private Boolean excludeInAutoSession;
        @SerializedName("SessionFlowId")
        @Expose
        private Integer sessionFlowId;
        @SerializedName("VideoId")
        @Expose
        private Integer videoId;
        @SerializedName("IsSquadSession")
        @Expose
        private Boolean isSquadSession;
        @SerializedName("IsAbbbcOnlineSession")
        @Expose
        private Boolean isAbbbcOnlineSession;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProgram() {
            return program;
        }

        public void setProgram(String program) {
            this.program = program;
        }

        public String getSessionTitle() {
            return sessionTitle;
        }

        public void setSessionTitle(String sessionTitle) {
            this.sessionTitle = sessionTitle;
        }

        public String getSessionCode() {
            return sessionCode;
        }

        public void setSessionCode(String sessionCode) {
            this.sessionCode = sessionCode;
        }

        public Integer getSessionType() {
            return sessionType;
        }

        public void setSessionType(Integer sessionType) {
            this.sessionType = sessionType;
        }

        public Integer getBodyArea() {
            return bodyArea;
        }

        public void setBodyArea(Integer bodyArea) {
            this.bodyArea = bodyArea;
        }

        public Integer getWeekNumber() {
            return weekNumber;
        }

        public void setWeekNumber(Integer weekNumber) {
            this.weekNumber = weekNumber;
        }

        public Integer getMinFitness() {
            return minFitness;
        }

        public void setMinFitness(Integer minFitness) {
            this.minFitness = minFitness;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Integer getEquipmentRequired() {
            return equipmentRequired;
        }

        public void setEquipmentRequired(Integer equipmentRequired) {
            this.equipmentRequired = equipmentRequired;
        }

        public Integer getExerciseLocationId() {
            return exerciseLocationId;
        }

        public void setExerciseLocationId(Integer exerciseLocationId) {
            this.exerciseLocationId = exerciseLocationId;
        }

        public Integer getSessionSrNumber() {
            return sessionSrNumber;
        }

        public void setSessionSrNumber(Integer sessionSrNumber) {
            this.sessionSrNumber = sessionSrNumber;
        }

        public Integer getSessionCategory() {
            return sessionCategory;
        }

        public void setSessionCategory(Integer sessionCategory) {
            this.sessionCategory = sessionCategory;
        }

        public String getSessionOverviewText() {
            return sessionOverviewText;
        }

        public void setSessionOverviewText(String sessionOverviewText) {
            this.sessionOverviewText = sessionOverviewText;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Boolean getExcludeInAutoSession() {
            return excludeInAutoSession;
        }

        public void setExcludeInAutoSession(Boolean excludeInAutoSession) {
            this.excludeInAutoSession = excludeInAutoSession;
        }

        public Integer getSessionFlowId() {
            return sessionFlowId;
        }

        public void setSessionFlowId(Integer sessionFlowId) {
            this.sessionFlowId = sessionFlowId;
        }

        public Integer getVideoId() {
            return videoId;
        }

        public void setVideoId(Integer videoId) {
            this.videoId = videoId;
        }

        public Boolean getIsSquadSession() {
            return isSquadSession;
        }

        public void setIsSquadSession(Boolean isSquadSession) {
            this.isSquadSession = isSquadSession;
        }

        public Boolean getIsAbbbcOnlineSession() {
            return isAbbbcOnlineSession;
        }

        public void setIsAbbbcOnlineSession(Boolean isAbbbcOnlineSession) {
            this.isAbbbcOnlineSession = isAbbbcOnlineSession;
        }

    }

    public class SquadUserWorkoutSession {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("UserId")
        @Expose
        private Integer userId;
        @SerializedName("ExerciseSessionId")
        @Expose
        private Integer exerciseSessionId;
        @SerializedName("SessionTypeId")
        @Expose
        private Integer sessionTypeId;
        @SerializedName("StartDateTime")
        @Expose
        private String startDateTime;
        @SerializedName("StartDateTimeStr")
        @Expose
        private String startDateTimeStr;
        @SerializedName("DurationMinutes")
        @Expose
        private Integer durationMinutes;
        @SerializedName("IsSystemGenerated")
        @Expose
        private Boolean isSystemGenerated;
        @SerializedName("RepeatNextWeek")
        @Expose
        private Boolean repeatNextWeek;
        @SerializedName("ExerciseLocationId")
        @Expose
        private Integer exerciseLocationId;
        @SerializedName("IsDone")
        @Expose
        private Boolean isDone;
        @SerializedName("OrderNumber")
        @Expose
        private Integer orderNumber;
        @SerializedName("DateUpdated")
        @Expose
        private String dateUpdated;
        @SerializedName("RowTimeStamp")
        @Expose
        private String rowTimeStamp;
        @SerializedName("ExerciseSessionDetails")
        @Expose
        private ExerciseSessionDetails exerciseSessionDetails;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getExerciseSessionId() {
            return exerciseSessionId;
        }

        public void setExerciseSessionId(Integer exerciseSessionId) {
            this.exerciseSessionId = exerciseSessionId;
        }

        public Integer getSessionTypeId() {
            return sessionTypeId;
        }

        public void setSessionTypeId(Integer sessionTypeId) {
            this.sessionTypeId = sessionTypeId;
        }

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public String getStartDateTimeStr() {
            return startDateTimeStr;
        }

        public void setStartDateTimeStr(String startDateTimeStr) {
            this.startDateTimeStr = startDateTimeStr;
        }

        public Integer getDurationMinutes() {
            return durationMinutes;
        }

        public void setDurationMinutes(Integer durationMinutes) {
            this.durationMinutes = durationMinutes;
        }

        public Boolean getIsSystemGenerated() {
            return isSystemGenerated;
        }

        public void setIsSystemGenerated(Boolean isSystemGenerated) {
            this.isSystemGenerated = isSystemGenerated;
        }

        public Boolean getRepeatNextWeek() {
            return repeatNextWeek;
        }

        public void setRepeatNextWeek(Boolean repeatNextWeek) {
            this.repeatNextWeek = repeatNextWeek;
        }

        public Integer getExerciseLocationId() {
            return exerciseLocationId;
        }

        public void setExerciseLocationId(Integer exerciseLocationId) {
            this.exerciseLocationId = exerciseLocationId;
        }

        public Boolean getIsDone() {
            return isDone;
        }

        public void setIsDone(Boolean isDone) {
            this.isDone = isDone;
        }

        public Integer getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(Integer orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getDateUpdated() {
            return dateUpdated;
        }

        public void setDateUpdated(String dateUpdated) {
            this.dateUpdated = dateUpdated;
        }

        public String getRowTimeStamp() {
            return rowTimeStamp;
        }

        public void setRowTimeStamp(String rowTimeStamp) {
            this.rowTimeStamp = rowTimeStamp;
        }

        public ExerciseSessionDetails getExerciseSessionDetails() {
            return exerciseSessionDetails;
        }

        public void setExerciseSessionDetails(ExerciseSessionDetails exerciseSessionDetails) {
            this.exerciseSessionDetails = exerciseSessionDetails;
        }

    }



}
