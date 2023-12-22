package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by android-krishnendu on 1/27/17.
 */

public class QuickEditedCircuitList {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("ExerciseSessionId")
    @Expose
    private Integer exerciseSessionId;
    @SerializedName("SessionTitle")
    @Expose
    private String sessionTitle;
    @SerializedName("OldExerciseId")
    @Expose
    private Integer oldExerciseId;
    @SerializedName("NewExerciseId")
    @Expose
    private Integer newExerciseId;
    @SerializedName("Personalised")
    @Expose
    private Boolean personalised;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("SequenceNumber")
    @Expose
    private Integer sequenceNumber;
    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("UserSessionID")
    @Expose
    private Integer userSessionID;

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

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public Integer getOldExerciseId() {
        return oldExerciseId;
    }

    public void setOldExerciseId(Integer oldExerciseId) {
        this.oldExerciseId = oldExerciseId;
    }

    public Integer getNewExerciseId() {
        return newExerciseId;
    }

    public void setNewExerciseId(Integer newExerciseId) {
        this.newExerciseId = newExerciseId;
    }

    public Boolean getPersonalised() {
        return personalised;
    }

    public void setPersonalised(Boolean personalised) {
        this.personalised = personalised;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getUserSessionID() {
        return userSessionID;
    }

    public void setUserSessionID(Integer userSessionID) {
        this.userSessionID = userSessionID;
    }

}
