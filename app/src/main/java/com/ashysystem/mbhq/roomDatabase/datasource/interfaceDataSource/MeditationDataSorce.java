package com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource;

import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface MeditationDataSorce {

    Completable insertMeditation(MeditationEntity meditationEntity);

    Completable insertMeditations(List<MeditationEntity> meditationEntities);

    Completable updateMeditation(MeditationEntity meditationEntity);

    Single<List<MeditationEntity>> getAllMeditation();

    Single<List<MeditationEntity>> getAllDownloadedMeditation();

    void deleteAllMeditations();

    void deleteMeditationById(Integer meditationId);

    Single<Integer> getMeditationCountById(Integer meditationId);

    Single<MeditationEntity> getMeditationById(Integer meditationId);



}
