package com.ashysystem.mbhq.roomDatabase.datasource.localDataSource;

import com.ashysystem.mbhq.roomDatabase.dao.GrowthListDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GrowthAddDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalGrowthDataSource implements GrowthAddDataSource {

    GrowthListDao growthListDao;

    public LocalGrowthDataSource(GrowthListDao growthListDao)
    {
        this.growthListDao = growthListDao;
    }

    @Override
    public Completable insertGrowth(GrowthEntity growthEntity) {
        return growthListDao.insertGrowth(growthEntity);
    }

    @Override
    public Single<GrowthEntity> getGrowthById(Integer growthId) {
        return growthListDao.getGrowthById(growthId);
    }

    @Override
    public Single<List<GrowthEntity>> getAllGrowth() {
        return growthListDao.getAllGrowth();
    }

    @Override
    public Single<List<GrowthEntity>> getAllGrowthNotSynced() {
        return growthListDao.getAllGrowthNotSynced();
    }

    @Override
    public Single<List<GrowthEntity>> getAllGrowthByDate(String growthdate) {
        return growthListDao.getAllGrowthByDate(growthdate);
    }

    @Override
    public void deleteAllGrowth() {
        growthListDao.deleteAllGrowth();
    }

    @Override
    public void deleteGrowthById(Integer growthId) {
        growthListDao.deleteGrowthById(growthId);
    }

    @Override
    public void deleteGrowthByTimeStamp(String timeStamp) {
        growthListDao.deleteGrowthByTimeStamp(timeStamp);
    }

    @Override
    public Single<Integer> getCount(Integer growthId) {
        return growthListDao.getCount(growthId);
    }
}
