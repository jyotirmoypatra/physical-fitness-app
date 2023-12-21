package com.ashysystem.mbhq.roomDatabase.modelFactory;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitCalendarDataSource;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitCalendarViewModel;

public class ViewModelFactoryForHabitCalendar implements ViewModelProvider.Factory {

    private final HabitCalendarDataSource mDataSource;

    public ViewModelFactoryForHabitCalendar(HabitCalendarDataSource dataSource) {
        mDataSource = dataSource;
    }

    @Override
    @NonNull
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HabitCalendarViewModel.class)) {
            return (T) new HabitCalendarViewModel(mDataSource);
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
