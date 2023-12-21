package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HabbitCalendarModel {

    @SerializedName("HabitStats")
    @Expose
    private List<HabitStat> habitStats = null;
    @SerializedName("BreakHabitStats")
    @Expose
    private List<BreakHabitStat> breakHabitStats = null;
    @SerializedName("HabitMonthlyStats")
    @Expose
    private List<HabitMonthlyStat> habitMonthlyStats = null;
    @SerializedName("HabitQuarterlyStats")
    @Expose
    private List<HabitQuarterlyStat> habitQuarterlyStats = null;
    @SerializedName("HabitYearlyStats")
    @Expose
    private List<HabitYearlyStat> habitYearlyStats = null;
    @SerializedName("BreakMonthlyStats")
    @Expose
    private List<BreakMonthlyStat> breakMonthlyStats = null;
    @SerializedName("BreakQuarterlyStats")
    @Expose
    private List<BreakQuarterlyStat> breakQuarterlyStats = null;
    @SerializedName("BreakYearlyStats")
    @Expose
    private List<BreakYearlyStat> breakYearlyStats = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public List<HabitStat> getHabitStats() {
        return habitStats;
    }

    public void setHabitStats(List<HabitStat> habitStats) {
        this.habitStats = habitStats;
    }

    public List<BreakHabitStat> getBreakHabitStats() {
        return breakHabitStats;
    }

    public void setBreakHabitStats(List<BreakHabitStat> breakHabitStats) {
        this.breakHabitStats = breakHabitStats;
    }

    public List<HabitMonthlyStat> getHabitMonthlyStats() {
        return habitMonthlyStats;
    }

    public void setHabitMonthlyStats(List<HabitMonthlyStat> habitMonthlyStats) {
        this.habitMonthlyStats = habitMonthlyStats;
    }

    public List<HabitQuarterlyStat> getHabitQuarterlyStats() {
        return habitQuarterlyStats;
    }

    public void setHabitQuarterlyStats(List<HabitQuarterlyStat> habitQuarterlyStats) {
        this.habitQuarterlyStats = habitQuarterlyStats;
    }

    public List<HabitYearlyStat> getHabitYearlyStats() {
        return habitYearlyStats;
    }

    public void setHabitYearlyStats(List<HabitYearlyStat> habitYearlyStats) {
        this.habitYearlyStats = habitYearlyStats;
    }

    public List<BreakMonthlyStat> getBreakMonthlyStats() {
        return breakMonthlyStats;
    }

    public void setBreakMonthlyStats(List<BreakMonthlyStat> breakMonthlyStats) {
        this.breakMonthlyStats = breakMonthlyStats;
    }

    public List<BreakQuarterlyStat> getBreakQuarterlyStats() {
        return breakQuarterlyStats;
    }

    public void setBreakQuarterlyStats(List<BreakQuarterlyStat> breakQuarterlyStats) {
        this.breakQuarterlyStats = breakQuarterlyStats;
    }

    public List<BreakYearlyStat> getBreakYearlyStats() {
        return breakYearlyStats;
    }

    public void setBreakYearlyStats(List<BreakYearlyStat> breakYearlyStats) {
        this.breakYearlyStats = breakYearlyStats;
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
    ////////////////////
    public class BreakHabitStat {

        @SerializedName("TaskMasterId")
        @Expose
        private Integer taskMasterId;
        @SerializedName("ActionId")
        @Expose
        private Integer actionId;
        @SerializedName("GoalId")
        @Expose
        private Object goalId;
        @SerializedName("BucketListId")
        @Expose
        private Object bucketListId;
        @SerializedName("ReverseBucketListId")
        @Expose
        private Object reverseBucketListId;
        @SerializedName("VisionBoardId")
        @Expose
        private Object visionBoardId;
        @SerializedName("GratitudeListId")
        @Expose
        private Object gratitudeListId;
        @SerializedName("AffirmationId")
        @Expose
        private Object affirmationId;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("TaskDate")
        @Expose
        private String taskDate;
        @SerializedName("IsDone")
        @Expose
        private Boolean isDone;
        @SerializedName("Note")
        @Expose
        private Object note;
        @SerializedName("IsTaskDone")
        @Expose
        private Boolean isTaskDone;

        public Integer getTaskMasterId() {
            return taskMasterId;
        }

        public void setTaskMasterId(Integer taskMasterId) {
            this.taskMasterId = taskMasterId;
        }

        public Integer getActionId() {
            return actionId;
        }

        public void setActionId(Integer actionId) {
            this.actionId = actionId;
        }

        public Object getGoalId() {
            return goalId;
        }

        public void setGoalId(Object goalId) {
            this.goalId = goalId;
        }

        public Object getBucketListId() {
            return bucketListId;
        }

        public void setBucketListId(Object bucketListId) {
            this.bucketListId = bucketListId;
        }

        public Object getReverseBucketListId() {
            return reverseBucketListId;
        }

        public void setReverseBucketListId(Object reverseBucketListId) {
            this.reverseBucketListId = reverseBucketListId;
        }

        public Object getVisionBoardId() {
            return visionBoardId;
        }

        public void setVisionBoardId(Object visionBoardId) {
            this.visionBoardId = visionBoardId;
        }

        public Object getGratitudeListId() {
            return gratitudeListId;
        }

        public void setGratitudeListId(Object gratitudeListId) {
            this.gratitudeListId = gratitudeListId;
        }

        public Object getAffirmationId() {
            return affirmationId;
        }

        public void setAffirmationId(Object affirmationId) {
            this.affirmationId = affirmationId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTaskDate() {
            return taskDate;
        }

        public void setTaskDate(String taskDate) {
            this.taskDate = taskDate;
        }

        public Boolean getIsDone() {
            return isDone;
        }

        public void setIsDone(Boolean isDone) {
            this.isDone = isDone;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Boolean getIsTaskDone() {
            return isTaskDone;
        }

        public void setIsTaskDone(Boolean isTaskDone) {
            this.isTaskDone = isTaskDone;
        }

    }
    /////////////////////////////
    public class BreakMonthlyStat {

        @SerializedName("Month")
        @Expose
        private Integer month;
        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsMonthlyDone")
        @Expose
        private Integer statsMonthlyDone;
        @SerializedName("StatsMonthlyTotal")
        @Expose
        private Integer statsMonthlyTotal;
        @SerializedName("WeeklyStats")
        @Expose
        private List<WeeklyStat_> weeklyStats = null;
        @SerializedName("StatsMonthlyPercentage")
        @Expose
        private Double statsMonthlyPercentage;

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsMonthlyDone() {
            return statsMonthlyDone;
        }

        public void setStatsMonthlyDone(Integer statsMonthlyDone) {
            this.statsMonthlyDone = statsMonthlyDone;
        }

        public Integer getStatsMonthlyTotal() {
            return statsMonthlyTotal;
        }

        public void setStatsMonthlyTotal(Integer statsMonthlyTotal) {
            this.statsMonthlyTotal = statsMonthlyTotal;
        }

        public List<WeeklyStat_> getWeeklyStats() {
            return weeklyStats;
        }

        public void setWeeklyStats(List<WeeklyStat_> weeklyStats) {
            this.weeklyStats = weeklyStats;
        }

        public Double getStatsMonthlyPercentage() {
            return statsMonthlyPercentage;
        }

        public void setStatsMonthlyPercentage(Double statsMonthlyPercentage) {
            this.statsMonthlyPercentage = statsMonthlyPercentage;
        }

    }
    //////////////////
    public class BreakQuarterlyStat {

        @SerializedName("Quarter")
        @Expose
        private Integer quarter;
        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsQuarterlyDone")
        @Expose
        private Integer statsQuarterlyDone;
        @SerializedName("StatsQuarterlyTotal")
        @Expose
        private Integer statsQuarterlyTotal;
        @SerializedName("StatsQuarterlyPercentage")
        @Expose
        private Double statsQuarterlyPercentage;

        public Integer getQuarter() {
            return quarter;
        }

        public void setQuarter(Integer quarter) {
            this.quarter = quarter;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsQuarterlyDone() {
            return statsQuarterlyDone;
        }

        public void setStatsQuarterlyDone(Integer statsQuarterlyDone) {
            this.statsQuarterlyDone = statsQuarterlyDone;
        }

        public Integer getStatsQuarterlyTotal() {
            return statsQuarterlyTotal;
        }

        public void setStatsQuarterlyTotal(Integer statsQuarterlyTotal) {
            this.statsQuarterlyTotal = statsQuarterlyTotal;
        }

        public Double getStatsQuarterlyPercentage() {
            return statsQuarterlyPercentage;
        }

        public void setStatsQuarterlyPercentage(Double statsQuarterlyPercentage) {
            this.statsQuarterlyPercentage = statsQuarterlyPercentage;
        }

    }
    public class BreakYearlyStat {

        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsYearlyDone")
        @Expose
        private Integer statsYearlyDone;
        @SerializedName("StatsYearlyTotal")
        @Expose
        private Integer statsYearlyTotal;
        @SerializedName("StatsYearlyPercentage")
        @Expose
        private Double statsYearlyPercentage;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsYearlyDone() {
            return statsYearlyDone;
        }

        public void setStatsYearlyDone(Integer statsYearlyDone) {
            this.statsYearlyDone = statsYearlyDone;
        }

        public Integer getStatsYearlyTotal() {
            return statsYearlyTotal;
        }

        public void setStatsYearlyTotal(Integer statsYearlyTotal) {
            this.statsYearlyTotal = statsYearlyTotal;
        }

        public Double getStatsYearlyPercentage() {
            return statsYearlyPercentage;
        }

        public void setStatsYearlyPercentage(Double statsYearlyPercentage) {
            this.statsYearlyPercentage = statsYearlyPercentage;
        }

    }
    /////////////////////

    //////////////////
    public class HabitMonthlyStat {

        @SerializedName("Month")
        @Expose
        private Integer month;
        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsMonthlyDone")
        @Expose
        private Integer statsMonthlyDone;
        @SerializedName("StatsMonthlyTotal")
        @Expose
        private Integer statsMonthlyTotal;
        @SerializedName("WeeklyStats")
        @Expose
        private List<WeeklyStat> weeklyStats = null;
        @SerializedName("StatsMonthlyPercentage")
        @Expose
        private Double statsMonthlyPercentage;

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsMonthlyDone() {
            return statsMonthlyDone;
        }

        public void setStatsMonthlyDone(Integer statsMonthlyDone) {
            this.statsMonthlyDone = statsMonthlyDone;
        }

        public Integer getStatsMonthlyTotal() {
            return statsMonthlyTotal;
        }

        public void setStatsMonthlyTotal(Integer statsMonthlyTotal) {
            this.statsMonthlyTotal = statsMonthlyTotal;
        }

        public List<WeeklyStat> getWeeklyStats() {
            return weeklyStats;
        }

        public void setWeeklyStats(List<WeeklyStat> weeklyStats) {
            this.weeklyStats = weeklyStats;
        }

        public Double getStatsMonthlyPercentage() {
            return statsMonthlyPercentage;
        }

        public void setStatsMonthlyPercentage(Double statsMonthlyPercentage) {
            this.statsMonthlyPercentage = statsMonthlyPercentage;
        }

    }
    ////////////////////
    public class HabitQuarterlyStat {

        @SerializedName("Quarter")
        @Expose
        private Integer quarter;
        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsQuarterlyDone")
        @Expose
        private Integer statsQuarterlyDone;
        @SerializedName("StatsQuarterlyTotal")
        @Expose
        private Integer statsQuarterlyTotal;
        @SerializedName("StatsQuarterlyPercentage")
        @Expose
        private Double statsQuarterlyPercentage;

        public Integer getQuarter() {
            return quarter;
        }

        public void setQuarter(Integer quarter) {
            this.quarter = quarter;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsQuarterlyDone() {
            return statsQuarterlyDone;
        }

        public void setStatsQuarterlyDone(Integer statsQuarterlyDone) {
            this.statsQuarterlyDone = statsQuarterlyDone;
        }

        public Integer getStatsQuarterlyTotal() {
            return statsQuarterlyTotal;
        }

        public void setStatsQuarterlyTotal(Integer statsQuarterlyTotal) {
            this.statsQuarterlyTotal = statsQuarterlyTotal;
        }

        public Double getStatsQuarterlyPercentage() {
            return statsQuarterlyPercentage;
        }

        public void setStatsQuarterlyPercentage(Double statsQuarterlyPercentage) {
            this.statsQuarterlyPercentage = statsQuarterlyPercentage;
        }

    }
    /////////////////////////
    public class HabitStat {

        @SerializedName("TaskMasterId")
        @Expose
        private Integer taskMasterId;
        @SerializedName("ActionId")
        @Expose
        private Integer actionId;
        @SerializedName("GoalId")
        @Expose
        private Object goalId;
        @SerializedName("BucketListId")
        @Expose
        private Object bucketListId;
        @SerializedName("ReverseBucketListId")
        @Expose
        private Object reverseBucketListId;
        @SerializedName("VisionBoardId")
        @Expose
        private Object visionBoardId;
        @SerializedName("GratitudeListId")
        @Expose
        private Object gratitudeListId;
        @SerializedName("AffirmationId")
        @Expose
        private Object affirmationId;
        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("TaskDate")
        @Expose
        private String taskDate;
        @SerializedName("IsDone")
        @Expose
        private Boolean isDone;
        @SerializedName("Note")
        @Expose
        private String note;
        @SerializedName("IsTaskDone")
        @Expose
        private Boolean isTaskDone;

        private transient boolean showThickView = false;

        public boolean isShowThickView() {
            return showThickView;
        }

        public HabitStat setShowThickView(boolean showThickView) {
            this.showThickView = showThickView;
            return this;
        }

        public Integer getTaskMasterId() {
            return taskMasterId;
        }

        public void setTaskMasterId(Integer taskMasterId) {
            this.taskMasterId = taskMasterId;
        }

        public Integer getActionId() {
            return actionId;
        }

        public void setActionId(Integer actionId) {
            this.actionId = actionId;
        }

        public Object getGoalId() {
            return goalId;
        }

        public void setGoalId(Object goalId) {
            this.goalId = goalId;
        }

        public Object getBucketListId() {
            return bucketListId;
        }

        public void setBucketListId(Object bucketListId) {
            this.bucketListId = bucketListId;
        }

        public Object getReverseBucketListId() {
            return reverseBucketListId;
        }

        public void setReverseBucketListId(Object reverseBucketListId) {
            this.reverseBucketListId = reverseBucketListId;
        }

        public Object getVisionBoardId() {
            return visionBoardId;
        }

        public void setVisionBoardId(Object visionBoardId) {
            this.visionBoardId = visionBoardId;
        }

        public Object getGratitudeListId() {
            return gratitudeListId;
        }

        public void setGratitudeListId(Object gratitudeListId) {
            this.gratitudeListId = gratitudeListId;
        }

        public Object getAffirmationId() {
            return affirmationId;
        }

        public void setAffirmationId(Object affirmationId) {
            this.affirmationId = affirmationId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTaskDate() {
            return taskDate;
        }

        public void setTaskDate(String taskDate) {
            this.taskDate = taskDate;
        }

        public Boolean getIsDone() {
            return isDone;
        }

        public void setIsDone(Boolean isDone) {
            this.isDone = isDone;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Boolean getIsTaskDone() {
            return isTaskDone;
        }

        public void setIsTaskDone(Boolean isTaskDone) {
            this.isTaskDone = isTaskDone;
        }

    }
    ////////////////
    public class HabitYearlyStat {

        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsYearlyDone")
        @Expose
        private Integer statsYearlyDone;
        @SerializedName("StatsYearlyTotal")
        @Expose
        private Integer statsYearlyTotal;
        @SerializedName("StatsYearlyPercentage")
        @Expose
        private Double statsYearlyPercentage;

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsYearlyDone() {
            return statsYearlyDone;
        }

        public void setStatsYearlyDone(Integer statsYearlyDone) {
            this.statsYearlyDone = statsYearlyDone;
        }

        public Integer getStatsYearlyTotal() {
            return statsYearlyTotal;
        }

        public void setStatsYearlyTotal(Integer statsYearlyTotal) {
            this.statsYearlyTotal = statsYearlyTotal;
        }

        public Double getStatsYearlyPercentage() {
            return statsYearlyPercentage;
        }

        public void setStatsYearlyPercentage(Double statsYearlyPercentage) {
            this.statsYearlyPercentage = statsYearlyPercentage;
        }

    }
    public class WeeklyStat {

        @SerializedName("WeekStartDate")
        @Expose
        private String weekStartDate;
        @SerializedName("Month")
        @Expose
        private Integer month;
        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsWeeklyDone")
        @Expose
        private Integer statsWeeklyDone;
        @SerializedName("StatsWeeklyTotal")
        @Expose
        private Integer statsWeeklyTotal;
        @SerializedName("StatsWeeklyPercentage")
        @Expose
        private Double statsWeeklyPercentage;

        public String getWeekStartDate() {
            return weekStartDate;
        }

        public void setWeekStartDate(String weekStartDate) {
            this.weekStartDate = weekStartDate;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsWeeklyDone() {
            return statsWeeklyDone;
        }

        public void setStatsWeeklyDone(Integer statsWeeklyDone) {
            this.statsWeeklyDone = statsWeeklyDone;
        }

        public Integer getStatsWeeklyTotal() {
            return statsWeeklyTotal;
        }

        public void setStatsWeeklyTotal(Integer statsWeeklyTotal) {
            this.statsWeeklyTotal = statsWeeklyTotal;
        }

        public Double getStatsWeeklyPercentage() {
            return statsWeeklyPercentage;
        }

        public void setStatsWeeklyPercentage(Double statsWeeklyPercentage) {
            this.statsWeeklyPercentage = statsWeeklyPercentage;
        }

    }
    public class WeeklyStat_ {

        @SerializedName("WeekStartDate")
        @Expose
        private String weekStartDate;
        @SerializedName("Month")
        @Expose
        private Integer month;
        @SerializedName("Year")
        @Expose
        private Integer year;
        @SerializedName("StatsWeeklyDone")
        @Expose
        private Integer statsWeeklyDone;
        @SerializedName("StatsWeeklyTotal")
        @Expose
        private Integer statsWeeklyTotal;
        @SerializedName("StatsWeeklyPercentage")
        @Expose
        private Double statsWeeklyPercentage;

        public String getWeekStartDate() {
            return weekStartDate;
        }

        public void setWeekStartDate(String weekStartDate) {
            this.weekStartDate = weekStartDate;
        }

        public Integer getMonth() {
            return month;
        }

        public void setMonth(Integer month) {
            this.month = month;
        }

        public Integer getYear() {
            return year;
        }

        public void setYear(Integer year) {
            this.year = year;
        }

        public Integer getStatsWeeklyDone() {
            return statsWeeklyDone;
        }

        public void setStatsWeeklyDone(Integer statsWeeklyDone) {
            this.statsWeeklyDone = statsWeeklyDone;
        }

        public Integer getStatsWeeklyTotal() {
            return statsWeeklyTotal;
        }

        public void setStatsWeeklyTotal(Integer statsWeeklyTotal) {
            this.statsWeeklyTotal = statsWeeklyTotal;
        }

        public Double getStatsWeeklyPercentage() {
            return statsWeeklyPercentage;
        }

        public void setStatsWeeklyPercentage(Double statsWeeklyPercentage) {
            this.statsWeeklyPercentage = statsWeeklyPercentage;
        }

    }

}
