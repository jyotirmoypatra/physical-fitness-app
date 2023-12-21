package com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource;

import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Access point for managing user data.
 */
public interface HabitEditDataSource {

    /**
     * Gets the user from the data source.
     *
     * @return the user from the data source.
     */
    Single<HabitHackerEdit> getHabitDetails(Integer habitId);

    /**
     * Inserts the user into the data source, or, if this is an existing user, updates it.
     *
     * @param habitHackerEdit the user to be inserted or updated.
     */
    Completable insertHabitEdit(HabitHackerEdit habitHackerEdit);

    /**
     * Deletes all users from the data source.
     */
    void deleteAllhabitEdits();

    void deleteByHabitId(Integer habitId);

    void deleteByHabitIds(List<Integer> habitIds);

    Single<Integer> getCount(Integer habitId);

}
