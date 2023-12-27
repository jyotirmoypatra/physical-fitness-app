package com.ashysystem.mbhq.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by android-krishnendu on 2/23/17.
 */

public class RelatedTask {

    @SerializedName("TaskId")
    @Expose
    private Integer taskId;
    @SerializedName("TaskTitle")
    @Expose
    private String taskTitle;
    @SerializedName("Instructions")
    @Expose
    private List<String> instructions = null;
    @SerializedName("TaskText")
    @Expose
    private String taskText;
    @SerializedName("IsDone")
    @Expose
    private Boolean isDone;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

}
