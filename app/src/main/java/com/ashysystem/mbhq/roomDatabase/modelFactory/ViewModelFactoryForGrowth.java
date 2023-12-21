package com.ashysystem.mbhq.roomDatabase.modelFactory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GrowthAddDataSource;
import com.ashysystem.mbhq.roomDatabase.viewModel.GrowthViewModel;

public class ViewModelFactoryForGrowth implements ViewModelProvider.Factory{

    private final GrowthAddDataSource mDataSource;

    public ViewModelFactoryForGrowth(GrowthAddDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GrowthViewModel.class)) {
            return (T) new GrowthViewModel(mDataSource);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
