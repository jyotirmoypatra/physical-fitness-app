package com.ashysystem.mbhq.roomDatabase.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface HabitEditDao {

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param habitHackerEdit the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertHabitEdit(HabitHackerEdit habitHackerEdit);

    @Query("SELECT * FROM habithackeredit WHERE habitId = :habitId LIMIT 1")
    Single<HabitHackerEdit> getHabitDetails(Integer habitId);

    /**
     * Delete all habithackeredit.
     */
    @Query("DELETE FROM habithackeredit")
    void deleteAllhabitEdits();

    @Query("DELETE FROM habithackeredit WHERE habitId = :habitId")
    void deleteByHabitId(Integer habitId);

    @Query("DELETE FROM habithackeredit WHERE habitId = (:habitIds)")
    void deleteByHabitIds(List<Integer> habitIds);

    @Query("SELECT COUNT(habitId) FROM habithackeredit WHERE habitId = :habitId")
    Single<Integer> getCount(Integer habitId);
}
