package com.ashysystem.mbhq.roomDatabase.modelFactory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.viewModel.HabitEditViewModel;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitEditDataSource;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final HabitEditDataSource mDataSource;

    public ViewModelFactory(HabitEditDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HabitEditViewModel.class)) {
            return (T) new HabitEditViewModel(mDataSource);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
