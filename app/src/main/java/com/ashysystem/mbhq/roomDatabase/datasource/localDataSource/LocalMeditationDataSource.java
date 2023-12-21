package com.ashysystem.mbhq.roomDatabase.datasource.localDataSource;

import com.ashysystem.mbhq.roomDatabase.dao.MeditationListDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.MeditationDataSorce;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalMeditationDataSource implements MeditationDataSorce {

    MeditationListDao meditationListDao;

    public LocalMeditationDataSource(MeditationListDao meditationListDao) {
        this.meditationListDao = meditationListDao;
    }

    @Override
    public Completable insertMeditation(MeditationEntity meditationEntity) {
        return meditationListDao.insertMeditation(meditationEntity);
    }

    @Override
    public Completable insertMeditations(List<MeditationEntity> meditationEntities) {
        return meditationListDao.insertMeditations(meditationEntities);
    }

    @Override
    public Completable updateMeditation(MeditationEntity meditationEntity) {
        return meditationListDao.updateMeditation(meditationEntity);
    }

    @Override
    public Single<List<MeditationEntity>> getAllMeditation() {
        return meditationListDao.getAllMeditation();
    }

    @Override
    public Single<List<MeditationEntity>> getAllDownloadedMeditation() {
        return meditationListDao.getAllDownloadedMeditation();
    }

    @Override
    public void deleteAllMeditations() {
        meditationListDao.deleteAllMeditations();
    }

    @Override
    public void deleteMeditationById(Integer meditationId) {
        meditationListDao.deleteMeditationById(meditationId);
    }

    @Override
    public Single<Integer> getMeditationCountById(Integer meditationId) {
        return meditationListDao.getMeditationCountById(meditationId);
    }

    @Override
    public Single<MeditationEntity> getMeditationById(Integer meditationId) {
        return meditationListDao.getMeditationById(meditationId);
    }
}
