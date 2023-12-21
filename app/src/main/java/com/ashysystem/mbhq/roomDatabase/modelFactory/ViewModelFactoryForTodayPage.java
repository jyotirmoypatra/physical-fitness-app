package com.ashysystem.mbhq.roomDatabase.modelFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.TodayPageDataSource;
import com.ashysystem.mbhq.roomDatabase.viewModel.TodayPageViewModel;

public class ViewModelFactoryForTodayPage implements ViewModelProvider.Factory{

    private final TodayPageDataSource mDataSource;

    public ViewModelFactoryForTodayPage(TodayPageDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TodayPageViewModel.class)) {
            return (T) new TodayPageViewModel(mDataSource);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
