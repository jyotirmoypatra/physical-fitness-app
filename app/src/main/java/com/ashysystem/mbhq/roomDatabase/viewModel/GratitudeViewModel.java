package com.ashysystem.mbhq.roomDatabase.viewModel;

import android.annotation.SuppressLint;


import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.MbhqDatabse;
import com.ashysystem.mbhq.roomDatabase.dao.GratitudeListDao;
import com.ashysystem.mbhq.roomDatabase.data_modular.GratitudeDataModular;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;
import com.ashysystem.mbhq.video.DemoApplication;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class GratitudeViewModel extends ViewModel {


    private final GratitudeAdddataSource mDataSource;

    public GratitudeViewModel(GratitudeAdddataSource dataSource) {
        mDataSource = dataSource;
    }

    public Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count)
    {
        return mDataSource.getAllAchive(position, count);
    }




/***********************unused*************************/
    public Completable insertGratitude(final GratitudeEntity gratitudeEntity) {
        return mDataSource.insertGratitude(gratitudeEntity);
    }


    public Single<List<GratitudeEntity>> getAllGratitudeNotSynced(){
        return mDataSource.getAllGratitudeNotSynced();
    }

    public Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate){
        return mDataSource.getAllGratitudeByDate(gratitudeDate);
    }

}
