package com.ashysystem.mbhq.roomDatabase.viewModel;



import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GrowthAddDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class GrowthViewModel extends ViewModel {

    private final GrowthAddDataSource mDataSource;

    public GrowthViewModel(GrowthAddDataSource dataSource) {
        mDataSource = dataSource;
    }

    GrowthEntity growthEntity;

    public Single<GrowthEntity> getGrowthById(Integer growthId) {
        return mDataSource.getGrowthById(growthId)
                // for every emission of the user, get the user name
                .map(user -> {
                    growthEntity = user;
                    return user;
                });
    }


    public Completable insertGrowth(final GrowthEntity growthEntity) {
        return mDataSource.insertGrowth(growthEntity);
    }

    public Single<Integer> getCount(Integer growthId)
    {
        return mDataSource.getCount(growthId);
    }

    public void deleteAllGrowth()
    {
        mDataSource.deleteAllGrowth();
    }

    public void deleteGrowthById(Integer growthId)
    {
        mDataSource.deleteGrowthById(growthId);
    }

    public void deleteGrowthByTimeStamp(String timeStamp)
    {
        mDataSource.deleteGrowthByTimeStamp(timeStamp);
    }

    public Single<List<GrowthEntity>> getAllGrowth()
    {
        return mDataSource.getAllGrowth();
    }

    public Single<List<GrowthEntity>> getAllGrowthNotSynced(){
        return mDataSource.getAllGrowthNotSynced();
    }

    public Single<List<GrowthEntity>> getAllGrowthByDate(String growthDate){
        return mDataSource.getAllGrowthByDate(growthDate);
    }

}
