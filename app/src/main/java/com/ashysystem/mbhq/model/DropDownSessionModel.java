package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class
DropDownSessionModel {
    @SerializedName("WorkoutSessionList")
    @Expose
    private List<WorkoutSessionList> workoutSessionList = null;
    @SerializedName("Success")
    @Expose
    private Boolean success;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<WorkoutSessionList> getWorkoutSessionList() {
        return workoutSessionList;
    }

    public void setWorkoutSessionList(List<WorkoutSessionList> workoutSessionList) {
        this.workoutSessionList = workoutSessionList;
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

    /////////////////////////////////////
    public class ExerciseSessionDetails {

        @SerializedName("Id")
        @Expose
        private Integer id;
        @SerializedName("Program")
        @Expose
        private Object program;
        @SerializedName("SessionTitle")
        @Expose
        private String sessionTitle;
        @SerializedName("SessionCode")
        @Expose
        private Object sessionCode;
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
        private Object sessionOverviewText;
        @SerializedName("UserId")
        @Expose
        private Object userId;
        @SerializedName("ExcludeInAutoSession")
        @Expose
        private Boolean excludeInAutoSession;
        @SerializedName("SessionFlowId")
        @Expose
        private Object sessionFlowId;
        @SerializedName("VideoId")
        @Expose
        private Object videoId;
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

        public Object getProgram() {
            return program;
        }

        public void setProgram(Object program) {
            this.program = program;
        }

        public String getSessionTitle() {
            return sessionTitle;
        }

        public void setSessionTitle(String sessionTitle) {
            this.sessionTitle = sessionTitle;
        }

        public Object getSessionCode() {
            return sessionCode;
        }

        public void setSessionCode(Object sessionCode) {
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

        public Object getSessionOverviewText() {
            return sessionOverviewText;
        }

        public void setSessionOverviewText(Object sessionOverviewText) {
            this.sessionOverviewText = sessionOverviewText;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Boolean getExcludeInAutoSession() {
            return excludeInAutoSession;
        }

        public void setExcludeInAutoSession(Boolean excludeInAutoSession) {
            this.excludeInAutoSession = excludeInAutoSession;
        }

        public Object getSessionFlowId() {
            return sessionFlowId;
        }

        public void setSessionFlowId(Object sessionFlowId) {
            this.sessionFlowId = sessionFlowId;
        }

        public Object getVideoId() {
            return videoId;
        }

        public void setVideoId(Object videoId) {
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

    /////////////////////////////////
    public class WorkoutSessionList {

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
        private Object startDateTimeStr;
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
        @SerializedName("IsFavourite")
        @Expose
        private Boolean isFavourite;
        @SerializedName("OrderNumber")
        @Expose
        private Integer orderNumber;
        @SerializedName("DateUpdated")
        @Expose
        private String dateUpdated;
        @SerializedName("RowTimeStamp")
        @Expose
        private Object rowTimeStamp;
        @SerializedName("IsSquadMiniProgram")
        @Expose
        private Boolean isSquadMiniProgram;
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

        public Object getStartDateTimeStr() {
            return startDateTimeStr;
        }

        public void setStartDateTimeStr(Object startDateTimeStr) {
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

        public Boolean getIsFavourite() {
            return isFavourite;
        }

        public void setIsFavourite(Boolean isFavourite) {
            this.isFavourite = isFavourite;
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

        public Object getRowTimeStamp() {
            return rowTimeStamp;
        }

        public void setRowTimeStamp(Object rowTimeStamp) {
            this.rowTimeStamp = rowTimeStamp;
        }

        public Boolean getIsSquadMiniProgram() {
            return isSquadMiniProgram;
        }

        public void setIsSquadMiniProgram(Boolean isSquadMiniProgram) {
            this.isSquadMiniProgram = isSquadMiniProgram;
        }

        public ExerciseSessionDetails getExerciseSessionDetails() {
            return exerciseSessionDetails;
        }

        public void setExerciseSessionDetails(ExerciseSessionDetails exerciseSessionDetails) {
            this.exerciseSessionDetails = exerciseSessionDetails;
        }
    }
}
