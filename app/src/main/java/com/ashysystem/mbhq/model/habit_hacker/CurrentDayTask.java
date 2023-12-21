package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CurrentDayTask implements Serializable {

    @SerializedName("TaskMasterId")
    @Expose
    private Integer taskMasterId;
    @SerializedName("ActionId")
    @Expose
    private Integer actionId;


    @SerializedName("TaskDate")
    @Expose
    private String taskDate;
    @SerializedName("IsDone")
    @Expose
    private Boolean isDone;


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



    public Boolean getIsTaskDone() {
        return isTaskDone;
    }

    public void setIsTaskDone(Boolean isTaskDone) {
        this.isTaskDone = isTaskDone;
    }

}
