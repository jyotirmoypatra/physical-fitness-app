package com.ashysystem.mbhq.roomDatabase.viewModel;



import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.MeditationDataSorce;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class MeditationViewModel extends ViewModel {

    private final MeditationDataSorce mDataSource;

    public MeditationViewModel(MeditationDataSorce dataSource) {
        mDataSource = dataSource;
    }

    public Completable insertMeditation(final MeditationEntity meditationEntity) {
        return mDataSource.insertMeditation(meditationEntity);
    }

    public Completable insertMeditations(final List<MeditationEntity> meditationEntities) {
        return mDataSource.insertMeditations(meditationEntities);
    }

    public Completable updateMeditation(final MeditationEntity meditationEntity) {
        return mDataSource.updateMeditation(meditationEntity);
    }

    public Single<List<MeditationEntity>> getAllMeditation()
    {
        return mDataSource.getAllMeditation();
    }

    public Single<List<MeditationEntity>> getAllDownloadedMeditation()
    {
        return mDataSource.getAllDownloadedMeditation();
    }

    public void deleteAllMeditations() {
        mDataSource.deleteAllMeditations();
    }

    public void deleteMeditationById(final int meditationId) {
        mDataSource.deleteMeditationById( meditationId);
    }

    public Single<Integer> getMeditationCountById(final int meditationId) {
        return mDataSource.getMeditationCountById( meditationId);
    }

    public Single<MeditationEntity> getMeditationById(final int meditationId) {
        return mDataSource.getMeditationById( meditationId);
    }




}
