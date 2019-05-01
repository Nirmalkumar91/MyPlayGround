package com.nish.android.playground.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserProfileEntity.class}, version = NishDatabase.VERSION)
public abstract class NishDatabase extends RoomDatabase {

    static final int VERSION = 1;

    public abstract UserProfileDao getUserProfileDao();
}
