package com.ashysystem.mbhq.roomDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ashysystem.mbhq.roomDatabase.entity.HabitCalendarEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface HabitCalendarDao {

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param habitCalendarEntity the user to be inserted.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertHabitCalendar(HabitCalendarEntity habitCalendarEntity);

    @Query("SELECT * FROM habitcalendarentity WHERE habitId = :habitId LIMIT 1")
    Single<HabitCalendarEntity> getHabitCalendar(Integer habitId);

    /**
     * Delete all habitcalendarentity.
     */
    @Query("DELETE FROM habitcalendarentity")
    void deleteAllhabitCalendar();

    @Query("DELETE FROM habitcalendarentity WHERE habitId = :habitId")
    void deleteHabitCalendarById(Integer habitId);

    @Query("DELETE FROM habitcalendarentity WHERE habitId = (:habitIds)")
    void deleteHabitCalendarByIds(List<Integer> habitIds);

    @Query("SELECT COUNT(habitId) FROM habitcalendarentity WHERE habitId = :habitId")
    Single<Integer> getCountForHabitCalendar(Integer habitId);

}
