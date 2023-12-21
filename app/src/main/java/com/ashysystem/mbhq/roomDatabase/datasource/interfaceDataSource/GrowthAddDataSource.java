package com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource;

import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface GrowthAddDataSource {

    Completable insertGrowth(GrowthEntity growthEntity);

    Single<GrowthEntity> getGrowthById(Integer growthId);

    Single<List<GrowthEntity>> getAllGrowth();

    Single<List<GrowthEntity>> getAllGrowthNotSynced();

    Single<List<GrowthEntity>> getAllGrowthByDate(String growthdate);

    void deleteAllGrowth();

    void deleteGrowthById(Integer growthId);

    void deleteGrowthByTimeStamp(String timeStamp);

    Single<Integer> getCount(Integer growthId);

}
