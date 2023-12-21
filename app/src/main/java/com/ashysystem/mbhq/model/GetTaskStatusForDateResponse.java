package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTaskStatusForDateResponse {

    @SerializedName("TotalTaskForTheDay")
    @Expose
    private Integer totalTaskForTheDay;

    @SerializedName("TotalTaskDoneForTheDay")
    @Expose
    private Integer totalTaskDoneForTheDay;

    @SerializedName("DailyBadgeWon")
    @Expose
    private Boolean dailyBadgeWon;

    @SerializedName("DailyBadgeShown")
    @Expose
    private Boolean dailyBadgeShown;

    @SerializedName("WeekStartDate")
    @Expose
    private String weekStartDate;

    @SerializedName("WeeklyBadgeWon")
    @Expose
    private Boolean weeklyBadgeWon;

    @SerializedName("WeeklyBadgeShown")
    @Expose
    private Boolean weeklyBadgeShown;

    @SerializedName("DayStatuses")
    @Expose
    private List<DayStatus> dayStatuses;

    @SerializedName("DaysDoneForTheWeek")
    @Expose
    private Integer daysDoneForTheWeek;

    @SerializedName("SuccessFlag")
    @Expose
    private Boolean SuccessFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private String ErrorMessage;

    public Boolean getDailyBadgeShown() {
        return dailyBadgeShown;
    }

    public Boolean getDailyBadgeWon() {
        return dailyBadgeWon;
    }

    public List<DayStatus> getDayStatuses() {
        return dayStatuses;
    }

    public Boolean getWeeklyBadgeShown() {
        return weeklyBadgeShown;
    }

    public void setWeeklyBadgeShown(boolean isWeeklyBadgeShown){
        this.weeklyBadgeShown = isWeeklyBadgeShown;
    }

    public Boolean getWeeklyBadgeWon() {
        return weeklyBadgeWon;
    }

    public Integer getDaysDoneForTheWeek() {
        return daysDoneForTheWeek;
    }

    public void setDaysDoneForTheWeek(Integer daysDoneForTheWeek) {
        this.daysDoneForTheWeek = daysDoneForTheWeek;
    }

    public Integer getTotalTaskDoneForTheDay() {
        return totalTaskDoneForTheDay;
    }

    public Integer getTotalTaskForTheDay() {
        return totalTaskForTheDay;
    }

    public String getWeekStartDate() {
        return weekStartDate;
    }

    public Boolean getSuccessFlag() {
        return SuccessFlag;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public class DayStatus {

        @SerializedName("WeekDate")
        @Expose
        private String weekDate;

        @SerializedName("DoneCount")
        private Integer doneCount;

        @SerializedName("Total")
        private Integer total;

        public Integer getDoneCount() {
            return doneCount;
        }

        public Integer getTotal() {
            return total;
        }

        public String getWeekDate() {
            return weekDate;
        }

    }


}
