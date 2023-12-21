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
    public Completable insertGratitude(GratitudeEntity gratitudeEntity) {
        return gratitudeListDao.insertGratitude(gratitudeEntity);
    }

    /*@Override
    public Completable insertGratitudeNew(GratitudeEntityNew gratitudeEntity) {
        return gratitudeListDao.insertGratitudesNew(gratitudeEntity);
    }*/

    @Override
    public Completable insertGratitudes(List<GratitudeEntity> gratitudeEntities) {
        return gratitudeListDao.insertGratitudes(gratitudeEntities);
    }

    @Override
    public Single<GratitudeEntity> getGratitudeById(Integer gratitudeId) {
        return gratitudeListDao.getGratitudeById(gratitudeId);
    }

    @Override
    public Single<List<GratitudeEntity>> getAllGratitude() {
        return gratitudeListDao.getAllGratitude();
    }

    @Override
    public Single<List<GratitudeEntity>> getAllGratitudeNotSynced() {
        return gratitudeListDao.getAllGratitudeNotSynced();
    }

    @Override
    public Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate) {
        return gratitudeListDao.getAllGratitudeByDate(gratitudeDate);
    }

    @Override
    public Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count) {
        return gratitudeListDao.getAllAchive(position,count);
    }

    @Override
    public void deleteAllGratitudeNew() {
         gratitudeListDao.deleteAllGratitudeNew();
    }

    @Override
    public Completable deleteAllGratitudeNew_() {
        return gratitudeListDao.deleteAllGratitudeNew_();
    }

    /*  @Override
      public Completable deleteAllGratitudeNew_() {
         return gratitudeListDao.deleteAllGratitudeNew_();
      }*/
    @Override
    public Single<List<MyAchievementsListInnerModel>> getAllAchievements() {
        return gratitudeListDao.getAllAchievements();
    }


    @Override
    public void deleteAllGratitude() {
        gratitudeListDao.deleteAllGratitude();
    }



    @Override
    public void deleteGratitudeById(Integer gratitudeId) {
        gratitudeListDao.deleteGratitudeById(gratitudeId);
    }

    @Override
    public void deleteGratitudeByTimeStamp(String timeStamp) {
        gratitudeListDao.deleteGratitudeByTimeStamp(timeStamp);
    }

    @Override
    public Single<Integer> getCount(Integer habitId) {
        return gratitudeListDao.getCount(habitId);
    }
}
