package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetGratitudeCacheExpiryTimeResponse {
    /*{
        "MeditationExpiryDateTime": "2022-10-16 03:28:51",
            "HasHabitExpired": false,
            "HabitExpiryDateTime": "2020-1-1 00:00:00",
            "HasEqJournalExpired": false,
            "EqJournalExpiryDateTime": "2020-1-1 00:00:00",
            "SuccessFlag": true,
            "ErrorMessage": ""
    }*/

    @SerializedName("MeditationExpiryDateTime")
    @Expose
    private String MeditationExpiryDateTime;

    @SerializedName("HasHabitExpired")
    @Expose
    private boolean HasHabitExpired;

    @SerializedName("HabitExpiryDateTime")
    @Expose
    private String HabitExpiryDateTime;

    @SerializedName("EqJournalExpiryDateTime")
    @Expose
    private String EqJournalExpiryDateTime;

    @SerializedName("HasEqJournalExpired")
    @Expose
    private boolean HasEqJournalExpired;

    @SerializedName("SuccessFlag")
    @Expose
    private boolean SuccessFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    public String getMeditationExpiryDateTime() {
        return MeditationExpiryDateTime;
    }

    public void setMeditationExpiryDateTime(String meditationExpiryDateTime) {
        MeditationExpiryDateTime = meditationExpiryDateTime;
    }

    public boolean isHasHabitExpired() {
        return HasHabitExpired;
    }

    public void setHasHabitExpired(boolean hasHabitExpired) {
        HasHabitExpired = hasHabitExpired;
    }

    public String getHabitExpiryDateTime() {
        return HabitExpiryDateTime;
    }

    public void setHabitExpiryDateTime(String habitExpiryDateTime) {
        HabitExpiryDateTime = habitExpiryDateTime;
    }

    public String getEqJournalExpiryDateTime() {
        return EqJournalExpiryDateTime;
    }

    public void setEqJournalExpiryDateTime(String eqJournalExpiryDateTime) {
        EqJournalExpiryDateTime = eqJournalExpiryDateTime;
    }

    public boolean isHasEqJournalExpired() {
        return HasEqJournalExpired;
    }

    public void setHasEqJournalExpired(boolean hasEqJournalExpired) {
        HasEqJournalExpired = hasEqJournalExpired;
    }

    public boolean isSuccessFlag() {
        return SuccessFlag;
    }

    public void setSuccessFlag(boolean successFlag) {
        SuccessFlag = successFlag;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
