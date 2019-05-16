package com.nish.android.playground.userdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface UserProfileDao {

    @Query("SELECT * FROM " + UserProfileEntity.TABLE + " WHERE " + UserProfileEntity.COL_EMAIL + " LIKE :email")
    Single<UserProfileEntity> getUserProfile(String email);

    @Query("SELECT " + UserProfileEntity.COL_ACCESS_TOKEN + " FROM " + UserProfileEntity.TABLE + " WHERE " + UserProfileEntity.COL_EMAIL + " LIKE :email")
    String getAccessToken(String email);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserProfileEntity userProfile);

    @Query("DELETE FROM " + UserProfileEntity.TABLE + " WHERE " + UserProfileEntity.COL_EMAIL + " LIKE :email")
    void deleteUserProfile(String email);
}
