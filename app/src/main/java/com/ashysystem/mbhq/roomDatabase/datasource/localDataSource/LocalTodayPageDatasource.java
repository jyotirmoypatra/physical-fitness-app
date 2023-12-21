package com.ashysystem.mbhq.roomDatabase.datasource.localDataSource;

import com.ashysystem.mbhq.roomDatabase.dao.TodayHomePageDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.TodayPageDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.TodayHomePageEntitiy;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalTodayPageDatasource implements TodayPageDataSource {

    TodayHomePageDao todayHomePageDao;

    public LocalTodayPageDatasource(TodayHomePageDao todayHomePageDao)
    {
        this.todayHomePageDao = todayHomePageDao;
    }

    @Override
    public Completable insertTodayPage(TodayHomePageEntitiy todayHomePageEntitiy) {
        return todayHomePageDao.insertTodayPage(todayHomePageEntitiy);
    }

    @Override
    public Single<List<TodayHomePageEntitiy>> getTodayPageDetailsByDate(String todayPageDate) {
        return todayHomePageDao.getTodayPageDetailsByDate(todayPageDate);
    }


    @Override
    public void deleteAllGratitude() {
        todayHomePageDao.deleteAllGratitude();
    }

    @Override
    public void deleteGratitudeByDate(String todayPageDate) {
        todayHomePageDao.deleteGratitudeByDate(todayPageDate);
    }
}
