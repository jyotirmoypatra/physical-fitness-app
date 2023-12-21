package com.ashysystem.mbhq.roomDatabase.newpkg;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "gratitude_id")
    public Integer gratitudeId;
}
