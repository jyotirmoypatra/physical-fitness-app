package com.ashysystem.mbhq.roomDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ashysystem.mbhq.roomDatabase.entity.TodayHomePageEntitiy;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface TodayHomePageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertTodayPage(TodayHomePageEntitiy todayHomePageEntitiy);

    @Query("SELECT * FROM todayhomepage WHERE todayPageDate LIKE '%' || :todayPageDate || '%'")
    Single<List<TodayHomePageEntitiy>> getTodayPageDetailsByDate(String todayPageDate);

    @Query("DELETE FROM todayhomepage")
    void deleteAllGratitude();

    @Query("DELETE FROM todayhomepage WHERE todayPageDate LIKE '%' || :todayPageDate || '%'")
    void deleteGratitudeByDate(String todayPageDate);

}
