package com.ashysystem.mbhq.roomDatabase.datasource.localDataSource;

import com.ashysystem.mbhq.roomDatabase.dao.HabitCalendarDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitCalendarDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.HabitCalendarEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class LocalHabitCalenderDataSource implements HabitCalendarDataSource {

    private final HabitCalendarDao mUserDao;

    public LocalHabitCalenderDataSource(HabitCalendarDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Single<HabitCalendarEntity> getHabitCalendar(Integer habitId) {
        return mUserDao.getHabitCalendar(habitId);
    }

    @Override
    public Completable insertHabitCalendar(HabitCalendarEntity habitCalendarEntity) {
        return mUserDao.insertHabitCalendar(habitCalendarEntity);
    }

    @Override
    public void deleteAllhabitCalendar() {
        mUserDao.deleteAllhabitCalendar();
    }

    @Override
    public void deleteHabitCalendarById(Integer habitId) {
        mUserDao.deleteHabitCalendarById(habitId);
    }

    @Override
    public void deleteHabitCalendarByIds(List<Integer> habitIds) {
        mUserDao.deleteHabitCalendarByIds(habitIds);
    }

    @Override
    public Single<Integer> getCountForHabitCalendar(Integer habitId) {
        return mUserDao.getCountForHabitCalendar(habitId);
    }
}
