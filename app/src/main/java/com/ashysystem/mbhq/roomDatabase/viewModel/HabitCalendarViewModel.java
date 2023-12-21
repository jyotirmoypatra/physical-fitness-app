package com.ashysystem.mbhq.roomDatabase.viewModel;


import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitCalendarDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.HabitCalendarEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class HabitCalendarViewModel extends ViewModel {

    private final HabitCalendarDataSource mDataSource;

    private HabitCalendarEntity habitCalendarEntity;

    public HabitCalendarViewModel(HabitCalendarDataSource dataSource) {
        mDataSource = dataSource;
    }


    public Single<HabitCalendarEntity> getHabitCalendar(Integer habitId) {
        return mDataSource.getHabitCalendar(habitId)
                // for every emission of the user, get the user name
                .map(user -> {
                    habitCalendarEntity = user;
                    return user;
                });

    }


    public Completable insertHabitCalendar(final HabitCalendarEntity habitCalendarEntity) {
        return mDataSource.insertHabitCalendar(habitCalendarEntity);
    }

    public Single<Integer> getCountForHabitCalendar(Integer habitId)
    {
        return mDataSource.getCountForHabitCalendar(habitId);
    }

    public void deleteAllhabitCalendar()
    {
        mDataSource.deleteAllhabitCalendar();
    }

    public void deleteHabitCalendarById(Integer habitId)
    {
        mDataSource.deleteHabitCalendarById(habitId);
    }

    public void deleteHabitCalendarByIds(List<Integer> habitIds)
    {
        mDataSource.deleteHabitCalendarByIds(habitIds);
    }

}
