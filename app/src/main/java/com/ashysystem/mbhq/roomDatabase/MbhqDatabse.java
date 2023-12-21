package com.ashysystem.mbhq.roomDatabase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.dao.GratitudeListDao;
import com.ashysystem.mbhq.roomDatabase.dao.GrowthListDao;
import com.ashysystem.mbhq.roomDatabase.dao.HabitCalendarDao;
import com.ashysystem.mbhq.roomDatabase.dao.HabitEditDao;
import com.ashysystem.mbhq.roomDatabase.dao.MeditationListDao;
import com.ashysystem.mbhq.roomDatabase.dao.TodayHomePageDao;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;

import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;
import com.ashysystem.mbhq.roomDatabase.entity.HabitCalendarEntity;
import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;
import com.ashysystem.mbhq.roomDatabase.entity.TodayHomePageEntitiy;
import com.ashysystem.mbhq.roomDatabase.newpkg.User;

/**
 * The Room database that contains the Users table
 */
@Database(entities = {HabitHackerEdit.class, HabitCalendarEntity.class, GratitudeEntity.class , TodayHomePageEntitiy.class, GrowthEntity.class, MeditationEntity.class, User.class, MyAchievementsListInnerModel.class}, version = 14,exportSchema = false)
public abstract class MbhqDatabse extends RoomDatabase {

    private static volatile MbhqDatabse INSTANCE;

    public abstract HabitEditDao habitEditDao();
    public abstract HabitCalendarDao habitCalendarDao();
    public abstract GratitudeListDao gratitudeDao();
    public abstract TodayHomePageDao todayHomePageDao();
    public abstract GrowthListDao growthPageDao();
    public abstract MeditationListDao meditationListDao();
   // public abstract GratitudeDaoNew gratitudeDaoNew();

    public static MbhqDatabse getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MbhqDatabse.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MbhqDatabse.class, "MbhqDatabase.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
