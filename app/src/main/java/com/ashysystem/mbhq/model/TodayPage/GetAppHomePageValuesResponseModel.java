package com.ashysystem.mbhq.model.TodayPage;


import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.model.GetGratitudeListModelInner;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAppHomePageValuesResponseModel {

    @SerializedName("Gratitude")
    @Expose
    private GetGratitudeListModelInner gratitude;
    @SerializedName("Growth")
    @Expose
    private MyAchievementsListInnerModel growth;
    @SerializedName("Courses")
    @Expose
    private List<AvailableCourseModel.Course> courses = null;
    @SerializedName("Habits")
    @Expose
    private List<HabitSwap> habits = null;
    @SerializedName("SuccessFlag")
    @Expose
    private Boolean successFlag;
    @SerializedName("ErrorMessage")
    @Expose
    private String errorMessage;

    public GetGratitudeListModelInner getGratitude() {
        return gratitude;
    }

    public void setGratitude(GetGratitudeListModelInner gratitude) {
        this.gratitude = gratitude;
    }

    public MyAchievementsListInnerModel getGrowth() {
        return growth;
    }

    public void setGrowth(MyAchievementsListInnerModel growth) {
        this.growth = growth;
    }

    public List<AvailableCourseModel.Course> getCourses() {
        return courses;
    }

    public void setCourses(List<AvailableCourseModel.Course> courses) {
        this.courses = courses;
    }

    public List<HabitSwap> getHabits() {
        return habits;
    }

    public void setHabits(List<HabitSwap> habits) {
        this.habits = habits;
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
