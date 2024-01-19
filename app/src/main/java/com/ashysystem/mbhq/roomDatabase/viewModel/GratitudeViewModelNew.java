package com.ashysystem.mbhq.roomDatabase.viewModel;


import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.dao.GratitudeListDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class GratitudeViewModelNew extends ViewModel {

    private final GratitudeListDao mGratitudeListDao;

    public GratitudeViewModelNew(GratitudeListDao gratitudeListDao) {
        mGratitudeListDao = gratitudeListDao;
    }

    public Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position, Integer count) {
        return mGratitudeListDao.getAllAchive(position, count);
    }
}
