package com.ashysystem.mbhq.roomDatabase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;



@Entity(tableName = "meditation", indices = { @Index( value={ "meditationId" }, unique = true ) } )
public class MeditationEntity {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "meditationId")
    private Integer meditationId;

    @ColumnInfo(name = "meditationDetails")
    private String meditationDetails;

    @ColumnInfo(name = "isDownloaded")
    private boolean isDownloaded;

    @ColumnInfo(name = "isReload")
    private Boolean isReload;

    @NonNull
    public Integer getMeditationId() {
        return meditationId;
    }

    public MeditationEntity setMeditationId(@NonNull Integer meditationId) {
        this.meditationId = meditationId;
        return this;
    }

    public MeditationEntity(@NonNull Integer meditationId, String meditationDetails, Boolean isDownloaded, Boolean isReload)
    {
        this.meditationId = meditationId;
        this.meditationDetails = meditationDetails;
        this.isReload = isReload;
        this.isDownloaded = isDownloaded;
    }

    public String getMeditationDetails() {
        return meditationDetails;
    }

    public MeditationEntity setMeditationDetails(String meditationDetails) {
        this.meditationDetails = meditationDetails;
        return this;
    }

    public Boolean getIsDownloaded() {
        return isDownloaded;
    }

    public MeditationEntity setIsDownloaded(Boolean isDownloaded) {
        this.isDownloaded = isDownloaded;
        return this;
    }

    public Boolean getReload() {
        return isReload;
    }

    public MeditationEntity setReload(Boolean reload) {
        isReload = reload;
        return this;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
