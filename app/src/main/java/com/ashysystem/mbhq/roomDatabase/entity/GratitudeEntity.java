package com.ashysystem.mbhq.roomDatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "gratitude")
public class GratitudeEntity {

    @ColumnInfo(name = "gratitudeId")
    private Integer gratitudeId;

    @ColumnInfo(name = "gratitudeDetails")
    private String gratitudeDetails;

    @ColumnInfo(name = "gratitudeDate")
    private String gratitudeDate;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    private String timeStamp;

    @ColumnInfo(name = "isSync")
    private Boolean isSync;

    public GratitudeEntity(@NonNull Integer gratitudeId, String gratitudeDate, String gratitudeDetails, @NotNull String timeStamp, Boolean isSync)
    {
        this.gratitudeId = gratitudeId;
        this.gratitudeDate = gratitudeDate;
        this.gratitudeDetails = gratitudeDetails;
        this.timeStamp = timeStamp;
        this.isSync = isSync;
    }

    @NonNull
    public Integer getGratitudeId() {
        return gratitudeId;
    }

    public GratitudeEntity setGratitudeId(@NonNull Integer gratitudeId) {
        this.gratitudeId = gratitudeId;
        return this;
    }

    public String getGratitudeDetails() {
        return gratitudeDetails;
    }

    public GratitudeEntity setGratitudeDetails(String gratitudeDetails) {
        this.gratitudeDetails = gratitudeDetails;
        return this;
    }

    public Boolean getSync() {
        return isSync;
    }

    public GratitudeEntity setSync(Boolean sync) {
        isSync = sync;
        return this;
    }

    public String getGratitudeDate() {
        return gratitudeDate;
    }

    public GratitudeEntity setGratitudeDate(String gratitudeDate) {
        this.gratitudeDate = gratitudeDate;
        return this;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public GratitudeEntity setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

}
