package com.ashysystem.mbhq.roomDatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "growth")
public class GrowthEntity {

    @ColumnInfo(name = "growthId")
    private Integer growthId;

    @ColumnInfo(name = "growthDetails")
    private String growthDetails;

    @ColumnInfo(name = "growthDate")
    private String growthDate;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "timeStamp")
    private String timeStamp;

    @ColumnInfo(name = "isSync")
    private Boolean isSync;

    public GrowthEntity(@NonNull Integer growthId, String growthDate, String growthDetails, String timeStamp, Boolean isSync)
    {
        this.growthId = growthId;
        this.growthDate = growthDate;
        this.growthDetails = growthDetails;
        this.timeStamp = timeStamp;
        this.isSync = isSync;
    }

    public Integer getGrowthId() {
        return growthId;
    }

    public GrowthEntity setGrowthId(Integer growthId) {
        this.growthId = growthId;
        return this;
    }

    public String getGrowthDetails() {
        return growthDetails;
    }

    public GrowthEntity setGrowthDetails(String growthDetails) {
        this.growthDetails = growthDetails;
        return this;
    }

    public String getGrowthDate() {
        return growthDate;
    }

    public GrowthEntity setGrowthDate(String growthDate) {
        this.growthDate = growthDate;
        return this;
    }

    @NonNull
    public String getTimeStamp() {
        return timeStamp;
    }

    public GrowthEntity setTimeStamp(@NonNull String timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public Boolean getSync() {
        return isSync;
    }

    public GrowthEntity setSync(Boolean sync) {
        isSync = sync;
        return this;
    }

}
