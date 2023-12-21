package com.ashysystem.mbhq.roomDatabase.modelFactory;



import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModel;

public class ViewModelFactoryForGratitude implements ViewModelProvider.Factory{

    private final GratitudeAdddataSource mDataSource;

    public ViewModelFactoryForGratitude(GratitudeAdddataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GratitudeViewModel.class)) {
            return (T) new GratitudeViewModel(mDataSource);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
