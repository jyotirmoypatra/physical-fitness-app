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



    Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count);//////////




    /***********************unused*************************/

    Completable insertGratitude(GratitudeEntity gratitudeEntity);
    Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate);
    Single<List<GratitudeEntity>> getAllGratitudeNotSynced();

}
