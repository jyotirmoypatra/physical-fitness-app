package com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource;



import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;
//import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntityNew;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface GratitudeAdddataSource {

    Completable insertGratitude(GratitudeEntity gratitudeEntity);

   // Completable insertGratitudeNew(GratitudeEntityNew gratitudeEntity); //////

    Completable insertGratitudes(List<GratitudeEntity> gratitudeEntities);

    Single<GratitudeEntity> getGratitudeById(Integer gratitudeId);

    Single<List<GratitudeEntity>> getAllGratitude();

    Single<List<GratitudeEntity>> getAllGratitudeNotSynced();

    Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate);

    Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count);//////////
    void deleteAllGratitudeNew();
    Completable deleteAllGratitudeNew_();

    Single<List<MyAchievementsListInnerModel>> getAllAchievements();



    void deleteAllGratitude();



    void deleteGratitudeById(Integer gratitudeId);

    void deleteGratitudeByTimeStamp(String timeStamp);

    Single<Integer> getCount(Integer habitId);

}
