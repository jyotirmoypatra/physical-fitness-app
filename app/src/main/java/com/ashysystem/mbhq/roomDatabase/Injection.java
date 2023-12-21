package com.ashysystem.mbhq.roomDatabase;

import android.content.Context;

import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GratitudeAdddataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.GrowthAddDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitCalendarDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.HabitEditDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.MeditationDataSorce;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalGratitudeDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalGrowthDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalHabitCalenderDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalMeditationDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalTodayPageDatasource;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalUserDataSource;
import com.ashysystem.mbhq.roomDatabase.datasource.interfaceDataSource.TodayPageDataSource;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactory;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGratitude;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGrowth;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForHabitCalendar;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForMeditation;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForTodayPage;

public class Injection {

    public static HabitEditDataSource provideUserDataSource(Context context) {
        MbhqDatabse database = MbhqDatabse.getInstance(context);
        return new LocalUserDataSource(database.habitEditDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        HabitEditDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }

    //////////////
    public static HabitCalendarDataSource provideHabitCalendarDataSource(Context context) {
        MbhqDatabse database = MbhqDatabse.getInstance(context);
        return new LocalHabitCalenderDataSource(database.habitCalendarDao());
    }

    public static ViewModelFactoryForHabitCalendar provideViewModelFactoryHabitCalendar(Context context) {
        HabitCalendarDataSource dataSource = provideHabitCalendarDataSource(context);
        return new ViewModelFactoryForHabitCalendar(dataSource);
    }
    /////////////
    public static GratitudeAdddataSource provideGratitudeDataSource(Context context) {
        MbhqDatabse database = MbhqDatabse.getInstance(context);
        return new LocalGratitudeDataSource(database.gratitudeDao());
    }

    public static ViewModelFactoryForGratitude provideViewModelFactoryGratitude(Context context) {
        GratitudeAdddataSource dataSource = provideGratitudeDataSource(context);
        return new ViewModelFactoryForGratitude(dataSource);
    }

    ////////////////////
    public static TodayPageDataSource provideTodayPageDataSource(Context context) {
        MbhqDatabse database = MbhqDatabse.getInstance(context);
        return new LocalTodayPageDatasource(database.todayHomePageDao());
    }

    public static ViewModelFactoryForTodayPage provideViewModelFactoryTodayPage(Context context) {
        TodayPageDataSource dataSource = provideTodayPageDataSource(context);
        return new ViewModelFactoryForTodayPage(dataSource);
    }

    ////////////////////////////////////
    public static GrowthAddDataSource provideGrowthDataSource(Context context) {
        MbhqDatabse database = MbhqDatabse.getInstance(context);
        return new LocalGrowthDataSource(database.growthPageDao());
    }

    public static ViewModelFactoryForGrowth provideViewModelFactoryGrowth(Context context) {
        GrowthAddDataSource dataSource = provideGrowthDataSource(context);
        return new ViewModelFactoryForGrowth(dataSource);
    }

    ////////////////////////////////////
    public static MeditationDataSorce provideMeditationDataSource(Context context) {
        MbhqDatabse database = MbhqDatabse.getInstance(context);
        return new LocalMeditationDataSource(database.meditationListDao());
    }

    public static ViewModelFactoryForMeditation provideViewModelFactoryMeditation(Context context) {
        MeditationDataSorce dataSource = provideMeditationDataSource(context);
        return new ViewModelFactoryForMeditation(dataSource);
    }

}
