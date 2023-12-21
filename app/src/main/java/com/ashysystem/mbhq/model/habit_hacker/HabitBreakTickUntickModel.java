package com.ashysystem.mbhq.model.habit_hacker;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HabitBreakTickUntickModel {

    @SerializedName("TaskId")
    @Expose
    private Integer taskId;

    @SerializedName("IsDone")
    @Expose
    private Boolean isDone;

    @SerializedName("TickingMode")
    @Expose
    private Integer tickingMode;

    private Integer habitId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Integer getTickingMode() {
        return tickingMode;
    }

    public void setTickingMode(Integer tickingMode) {
        this.tickingMode = tickingMode;
    }

    public Integer getHabitId() {
        return habitId;
    }

    public void setHabitId(Integer habitId) {
        this.habitId = habitId;
    }
}
