package com.ashysystem.mbhq.roomDatabase.viewModel;


import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.TodayPageDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.TodayHomePageEntitiy;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class TodayPageViewModel extends ViewModel {

    private final TodayPageDataSource mDataSource;

    public TodayPageViewModel(TodayPageDataSource dataSource) {
        mDataSource = dataSource;
    }

    TodayHomePageEntitiy todayHomePageEntitiy;

    public Completable insertTodayPage(TodayHomePageEntitiy todayHomePageEntitiy){
        return mDataSource.insertTodayPage(todayHomePageEntitiy);
    }

    public Single<List<TodayHomePageEntitiy>> getTodayPageDetailsByDate(String todayPageDate){
        return mDataSource.getTodayPageDetailsByDate(todayPageDate);
    }

    public void deleteAllGratitude()
    {
        mDataSource.deleteAllGratitude();
    }

    public void deleteGratitudeByDate(String todayPageDate)
    {
        mDataSource.deleteGratitudeByDate(todayPageDate);
    }
}
