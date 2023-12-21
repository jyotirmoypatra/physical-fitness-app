package com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource;

import com.ashysystem.mbhq.roomDatabase.entity.TodayHomePageEntitiy;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TodayPageDataSource {

    Completable insertTodayPage(TodayHomePageEntitiy todayHomePageEntitiy);

    Single<List<TodayHomePageEntitiy>> getTodayPageDetailsByDate(String todayPageDate);

    void deleteAllGratitude();

    void deleteGratitudeByDate(String todayPageDate);
}
