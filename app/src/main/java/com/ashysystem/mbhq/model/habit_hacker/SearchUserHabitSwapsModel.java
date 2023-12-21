package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUserHabitSwapsModel {

    @SerializedName("HabitSwaps")
    @Expose
    private List<HabitSwap> habitSwaps = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<HabitSwap> getHabitSwaps() {
        return habitSwaps;
    }

    public void setHabitSwaps(List<HabitSwap> habitSwaps) {
        this.habitSwaps = habitSwaps;
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


}
