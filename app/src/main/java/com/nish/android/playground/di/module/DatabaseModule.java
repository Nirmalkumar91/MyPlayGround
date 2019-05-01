package com.nish.android.playground.di.module;

import android.app.Application;

import com.nish.android.playground.repository.NishDatabase;
import com.nish.android.playground.repository.NishRepository;
import com.nish.android.playground.repository.UserProfileDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    NishDatabase provideNishDatabase(Application application) {
        return Room.databaseBuilder(application, NishDatabase.class, "nish-contacts-db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    UserProfileDao provideUserProfileDao(NishDatabase database) {
        return database.getUserProfileDao();
    }

    @Provides
    @Singleton
    NishRepository provideNishRepository(UserProfileDao userProfileDao) {
        return new NishRepository(userProfileDao);
    }
}
