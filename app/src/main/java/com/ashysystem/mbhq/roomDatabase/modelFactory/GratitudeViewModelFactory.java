package com.ashysystem.mbhq.roomDatabase.modelFactory;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.dao.GratitudeListDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModelNew;

public class GratitudeViewModelFactory implements ViewModelProvider.Factory {

    private final GratitudeListDao gratitudeListDao;

    public GratitudeViewModelFactory(GratitudeListDao gratitudeListDao) {
        this.gratitudeListDao = gratitudeListDao;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GratitudeViewModelNew.class)) {
            return (T) new GratitudeViewModelNew(gratitudeListDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}