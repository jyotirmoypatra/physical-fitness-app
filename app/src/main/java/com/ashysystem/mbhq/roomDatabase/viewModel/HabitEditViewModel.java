package com.ashysystem.mbhq.roomDatabase.viewModel;



import androidx.lifecycle.ViewModel;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitEditDataSource;
import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class HabitEditViewModel extends ViewModel {

    private final HabitEditDataSource mDataSource;

    private HabitHackerEdit habitHackerEdit;

    public HabitEditViewModel(HabitEditDataSource dataSource) {
        mDataSource = dataSource;
    }

    /**
     * Get the user name of the user.
     *
     * @return a {@link Single} that will emit every time the user name has been updated.
     */
    public Single<HabitHackerEdit> getHabitDetails(Integer habitId) {
        return mDataSource.getHabitDetails(habitId)
                // for every emission of the user, get the user name
                .map(user -> {
                    habitHackerEdit = user;
                    return user;
                });

    }

    /**
     * Update the user name.
     *
     * @param habitHackerEdit the new user name
     * @return a {@link Completable} that completes when the user name is updated
     */
    public Completable insertUpdateUserName(final HabitHackerEdit habitHackerEdit) {
        // if there's no user, create a new user.
        // if we already have a user, then, since the user object is immutable,
        // create a new user, with the id of the previous user and the updated user name.
        return mDataSource.insertHabitEdit(habitHackerEdit);
    }

    public Single<Integer> getCount(Integer habitId)
    {
        return mDataSource.getCount(habitId);
    }

    public void deleteAllhabitEdits()
    {
        mDataSource.deleteAllhabitEdits();
    }

    public void deleteByHabitId(Integer habitId)
    {
        mDataSource.deleteByHabitId(habitId);
    }

    public void deleteByHabitIds(List<Integer> habitIds)
    {
        mDataSource.deleteByHabitIds(habitIds);
    }

}
