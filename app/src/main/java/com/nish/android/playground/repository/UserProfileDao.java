package com.nish.android.playground.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface UserProfileDao {

    @Query("SELECT * FROM " + UserProfileEntity.TABLE + " WHERE " + UserProfileEntity.COL_EMAIL + " LIKE :email")
    Single<UserProfileEntity> getUserProfile(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserProfileEntity userProfile);

    @Delete
    void remove(UserProfileEntity userProfile);
}
