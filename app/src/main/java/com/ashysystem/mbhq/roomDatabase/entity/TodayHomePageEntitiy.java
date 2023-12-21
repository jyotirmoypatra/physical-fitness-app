package com.ashysystem.mbhq.roomDatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todayhomepage")
public class TodayHomePageEntitiy {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "todayPageDate")
    private String todayPageDate;

    @ColumnInfo(name = "todayPageDetails")
    private String todayPageDetails;

    @ColumnInfo(name = "isReload")
    private Boolean isReload;

    @NonNull
    public String getTodayPageDate() {
        return todayPageDate;
    }

    public TodayHomePageEntitiy(@NonNull String todayPageDate,String todayPageDetails,Boolean isReload)
    {
        this.todayPageDate = todayPageDate;
        this.todayPageDetails = todayPageDetails;
        this.isReload = isReload;
    }

    public TodayHomePageEntitiy setTodayPageDate(@NonNull String todayPageDate) {
        this.todayPageDate = todayPageDate;
        return this;
    }

    public String getTodayPageDetails() {
        return todayPageDetails;
    }

    public TodayHomePageEntitiy setTodayPageDetails(String todayPageDetails) {
        this.todayPageDetails = todayPageDetails;
        return this;
    }

    public Boolean getReload() {
        return isReload;
    }

    public TodayHomePageEntitiy setReload(Boolean reload) {
        isReload = reload;
        return this;
    }

}
