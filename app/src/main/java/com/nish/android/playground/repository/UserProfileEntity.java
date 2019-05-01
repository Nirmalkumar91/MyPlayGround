package com.nish.android.playground.repository;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = UserProfileEntity.TABLE)
public class UserProfileEntity {

    public static final String TABLE = "user";
    public static final String COL_EMAIL = "email_id";
    public static final String COL_NAME = "name";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_ACCESS_TOKEN = "access_token";
    public static final String COL_REFRESH_TOKEN = "refresh_token";
    public static final String COL_PICTURE_URL = "picture_url";
    public static final String COL_EXPIRY_TIME = "expiry_time";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = COL_EMAIL)
    private String email;

    @ColumnInfo(name = COL_NAME)
    private String name;

    @ColumnInfo(name = COL_FIRST_NAME)
    private String firstName;

    @ColumnInfo(name = COL_LAST_NAME)
    private String lastName;

    @ColumnInfo(name = COL_ACCESS_TOKEN)
    private String accessToken;

    @ColumnInfo(name = COL_REFRESH_TOKEN)
    private String refreshToken;

    @ColumnInfo(name = COL_PICTURE_URL)
    private String pictureUrl;

    @ColumnInfo(name = COL_EXPIRY_TIME)
    private long expiryDate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }
}
