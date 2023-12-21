package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserHabitSwapModel {

    @SerializedName("HabitSwap")
    @Expose
    HabitSwap habitSwap;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public HabitSwap getHabitSwap() {
        return habitSwap;
    }

    public GetUserHabitSwapModel setHabitSwap(HabitSwap habitSwap) {
        this.habitSwap = habitSwap;
        return this;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public GetUserHabitSwapModel setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public GetUserHabitSwapModel setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
