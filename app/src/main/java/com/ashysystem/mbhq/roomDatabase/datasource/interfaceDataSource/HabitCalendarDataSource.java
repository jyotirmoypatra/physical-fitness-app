package com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource;

import com.ashysystem.mbhq.roomDatabase.entity.HabitCalendarEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface HabitCalendarDataSource {

    Single<HabitCalendarEntity> getHabitCalendar(Integer habitId);


    Completable insertHabitCalendar(HabitCalendarEntity habitCalendarEntity);


    void deleteAllhabitCalendar();

    void deleteHabitCalendarById(Integer habitId);

    void deleteHabitCalendarByIds(List<Integer> habitIds);

    Single<Integer> getCountForHabitCalendar(Integer habitId);

}
