package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GetWinTheWeekStatsResponse implements Serializable {

    @SerializedName("WeeklyWins")
    @Expose
    private Integer weeklyWins;

    @SerializedName("DayWins")
    @Expose
    private Integer dayWins;

    @SerializedName("DailyWinBrackets")
    @Expose
    private List<Wins> dailyWinBrackets;

    @SerializedName("WeeklyWinBrackets")
    @Expose
    private List<Wins> weeklyWinBrackets;

    @SerializedName("CurrentDailyStreak")
    @Expose
    private Integer currentDailyStreak;

    @SerializedName("PersonalBestDailyStreak")
    @Expose
    private Integer personalBestDailyStreak;

    @SerializedName("CurrentWeeklyStreak")
    @Expose
    private Integer CurrentWeeklyStreak;

    @SerializedName("PersonalBestWeeklyStreak")
    @Expose
    private Integer PersonalBestWeeklyStreak;

    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;

    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;


    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public Integer getDayWins() {
        return dayWins;
    }

    public Integer getWeeklyWins() {
        return weeklyWins;
    }

    public List<Wins> getDailyWinBrackets() {
        return dailyWinBrackets;
    }

    public List<Wins> getWeeklyWinBrackets() {
        return weeklyWinBrackets;
    }

    public Integer getCurrentDailyStreak() {
        return currentDailyStreak;
    }

    public Integer getCurrentWeeklyStreak() {
        return CurrentWeeklyStreak;
    }

    public Integer getPersonalBestDailyStreak() {
        return personalBestDailyStreak;
    }

    public Integer getPersonalBestWeeklyStreak() {
        return PersonalBestWeeklyStreak;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public class Wins implements Serializable{

        @SerializedName("Brackets")
        @Expose
        private List<DayStatus> brackets;

        @SerializedName("IsCurrentStreak")
        private Boolean isCurrentStreak;

        @SerializedName("Total")
        private Integer total;

        public List<DayStatus> getBrackets() {
            return brackets;
        }

        public Integer getTotal() {
            return total;
        }

        public Boolean getIsCurrentStreak() {
            return isCurrentStreak;
        }

        public class DayStatus implements Serializable {

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
}
