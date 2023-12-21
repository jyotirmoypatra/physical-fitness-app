package com.ashysystem.mbhq.roomDatabase.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull


@Entity(tableName = "gratitude_test")
data class GratitudeEntityNew(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int?,
        @ColumnInfo(name = "gratitudeId")
        var type: Int)