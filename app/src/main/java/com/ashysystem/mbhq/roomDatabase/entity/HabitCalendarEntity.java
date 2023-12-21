package com.ashysystem.mbhq.roomDatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "habitcalendarentity")
public class HabitCalendarEntity {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "habitId")
    private Integer habitId;

    @ColumnInfo(name = "habitDetails")
    private String habitDetails;

    @ColumnInfo(name = "isReload")
    private Boolean isReload;

    public HabitCalendarEntity(@NonNull Integer habitId,String habitDetails,Boolean isReload)
    {
        this.habitId = habitId;
        this.habitDetails = habitDetails;
        this.isReload = isReload;
    }

    @NonNull
    public Integer getHabitId() {
        return habitId;
    }

    public HabitCalendarEntity setHabitId(@NonNull Integer habitId) {
        this.habitId = habitId;
        return this;
    }

    public String getHabitDetails() {
        return habitDetails;
    }

    public HabitCalendarEntity setHabitDetails(String habitDetails) {
        this.habitDetails = habitDetails;
        return this;
    }

    public Boolean getReload() {
        return isReload;
    }

    public HabitCalendarEntity setReload(Boolean reload) {
        isReload = reload;
        return this;
    }

}
