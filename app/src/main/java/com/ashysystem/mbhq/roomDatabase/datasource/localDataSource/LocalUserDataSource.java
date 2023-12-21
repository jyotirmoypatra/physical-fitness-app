package com.ashysystem.mbhq.roomDatabase.datasource.localDataSource;

import com.ashysystem.mbhq.roomDatabase.dao.HabitEditDao;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitEditDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Using the Room database as a data source.
 */
public class LocalUserDataSource implements HabitEditDataSource {

    private final HabitEditDao mUserDao;

    public LocalUserDataSource(HabitEditDao userDao) {
        mUserDao = userDao;
    }

    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     */
    @Override
    public Single<HabitHackerEdit> getHabitDetails(Integer habitId) {
        return mUserDao.getHabitDetails(habitId);
    }

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param habitHackerEdit the user to be inserted or updated.
     */
    @Override
    public Completable insertHabitEdit(HabitHackerEdit habitHackerEdit) {
        return mUserDao.insertHabitEdit(habitHackerEdit);
    }

    /**
     * Deletes all users from the data source.
     */
    @Override
    public void deleteAllhabitEdits() {
        mUserDao.deleteAllhabitEdits();
    }

    @Override
    public void deleteByHabitId(Integer habitId) {
        mUserDao.deleteByHabitId(habitId);
    }

    @Override
    public void deleteByHabitIds(List<Integer> habitIds) {
        mUserDao.deleteByHabitIds(habitIds);
    }

    @Override
    public Single<Integer> getCount(Integer habitId) {
        return mUserDao.getCount(habitId);
    }
}
