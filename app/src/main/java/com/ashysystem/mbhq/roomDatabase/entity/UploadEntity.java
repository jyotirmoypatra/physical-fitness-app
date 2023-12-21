package com.ashysystem.mbhq.roomDatabase.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "upload")
public class UploadEntity {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "image_type")
    private int imageType;

    @ColumnInfo(name = "item_id")
    private int itemId;

    @ColumnInfo(name = "image_name")
    private String imageName;

    @ColumnInfo(name = "isSync")
    private Boolean isSync;

    public UploadEntity(int imageType, int itemId, String imageName, Boolean isSync) {
        this.imageType = imageType;
        this.itemId = itemId;
        this.imageName = imageName;
        this.isSync = isSync;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Boolean getSync() {
        return isSync;
    }

    public void setSync(Boolean sync) {
        isSync = sync;
    }
}
