package com.ashysystem.mbhq.roomDatabase.modelFactory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.MeditationDataSorce;
import com.ashysystem.mbhq.roomDatabase.viewModel.MeditationViewModel;

public class ViewModelFactoryForMeditation implements ViewModelProvider.Factory{

    private final MeditationDataSorce mDataSource;

    public ViewModelFactoryForMeditation(MeditationDataSorce dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeditationViewModel.class)) {
            return (T) new MeditationViewModel(mDataSource);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
