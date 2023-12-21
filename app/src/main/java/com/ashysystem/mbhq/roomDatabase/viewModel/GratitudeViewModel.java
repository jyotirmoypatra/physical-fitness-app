package com.ashysystem.mbhq.roomDatabase.viewModel;

import android.annotation.SuppressLint;


import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.dao.GratitudeListDao;
import com.ashysystem.mbhq.roomDatabase.data_modular.GratitudeDataModular;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class GratitudeViewModel extends ViewModel {

    private final GratitudeAdddataSource mDataSource;

    public GratitudeViewModel(GratitudeAdddataSource dataSource) {
        mDataSource = dataSource;
    }

    GratitudeEntity gratitudeEntity;

    public Single<GratitudeEntity> getGratitudeById(Integer habitId) {
        return mDataSource.getGratitudeById(habitId)
                // for every emission of the user, get the user name
                .map(user -> {
                    gratitudeEntity = user;
                    return user;
                });
    }


    public Completable insertGratitude(final GratitudeEntity gratitudeEntity) {
        return mDataSource.insertGratitude(gratitudeEntity);
    }

    public Completable insertGratitudes(List<GratitudeEntity> gratitudeEntities){
        return mDataSource.insertGratitudes(gratitudeEntities);
    }

   /* public Completable insertGratitudesNew(GratitudeEntityNew gratitudeEntitiesNew){
        return mDataSource.insertGratitudeNew(gratitudeEntitiesNew);
    }*/

    @SuppressLint("CheckResult")
    public Observable<Boolean> insertGratitudeNew_(GratitudeListDao dao, MyAchievementsListInnerModel myAchievementsListInnerModel){
        return Observable.create(emitter -> {
           // dao.insertGratitudesNew(new GratitudeDataModular().getEntityPickedItemFromProduct(myAchievementsListInnerModel))

            /*dao.insertGrat(
                    new GratitudeDataModular().getEntityPickedItemFromProduct(myAchievementsListInnerModel));
            emitter.onNext(true);*/
        });
    }

    public Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count)
    {
        return mDataSource.getAllAchive(position, count);
    }

    public  void deleteAllGratitudeNew()
    {
          mDataSource.deleteAllGratitudeNew();
    }
    public  Completable deleteAllGratitudeNew_()
    {
       return mDataSource.deleteAllGratitudeNew_();
    }
    public Single<List<MyAchievementsListInnerModel>> getAllAchivements()
    {
        return mDataSource.getAllAchievements();
    }

    public Single<Integer> getCount(Integer habitId)
    {
        return mDataSource.getCount(habitId);
    }

    public void deleteAllGratitude()
    {
        mDataSource.deleteAllGratitude();
    }



    public void deleteGratitudeById(Integer habitId)
    {
        mDataSource.deleteGratitudeById(habitId);
    }

    public void deleteGratitudeByTimeStamp(String timeStamp)
    {
        mDataSource.deleteGratitudeByTimeStamp(timeStamp);
    }

    public Single<List<GratitudeEntity>> getAllGratitude()
    {
        return mDataSource.getAllGratitude();
    }

    public Single<List<GratitudeEntity>> getAllGratitudeNotSynced(){
        return mDataSource.getAllGratitudeNotSynced();
    }

    public Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate){
        return mDataSource.getAllGratitudeByDate(gratitudeDate);
    }

}
