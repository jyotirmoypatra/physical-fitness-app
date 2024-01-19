package com.ashysystem.mbhq.roomDatabase.datasource.localDataSource;



import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.dao.GratitudeListDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;
//import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntityNew;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalGratitudeDataSource implements GratitudeAdddataSource {

    GratitudeListDao gratitudeListDao;

    public LocalGratitudeDataSource(GratitudeListDao gratitudeListDao)
    {
        this.gratitudeListDao = gratitudeListDao;
    }


    @Override
    public Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count) {
        return gratitudeListDao.getAllAchive(position,count);
    }





    /***********************unused*************************/

    @Override
    public Single<List<GratitudeEntity>> getAllGratitudeNotSynced() {
        return gratitudeListDao.getAllGratitudeNotSynced();
    }

    @Override
    public Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate) {
        return gratitudeListDao.getAllGratitudeByDate(gratitudeDate);
    }
    @Override
    public Completable insertGratitude(GratitudeEntity gratitudeEntity) {
        return gratitudeListDao.insertGratitude(gratitudeEntity);
    }
}
