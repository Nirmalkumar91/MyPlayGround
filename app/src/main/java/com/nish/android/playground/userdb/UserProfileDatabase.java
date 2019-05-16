package com.nish.android.playground.userdb;

import com.nish.android.playground.oauth.OAuthToken;
import com.nish.android.playground.oauth.UserProfile;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserProfileDatabase {

    private UserProfileDao userProfileDao;

    @Inject
    public UserProfileDatabase(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public String getAccessToken(String email) {
        return userProfileDao.getAccessToken(email);
    }

    public Single<UserProfile> getUserProfileSingle(String email) {
        return userProfileDao.getUserProfile(email).map(entity -> convertUserProfile(entity));
    }

    public UserProfile getUserProfile(String email) {
        return userProfileDao.getUserProfile(email).map(entity -> convertUserProfile(entity)).blockingGet();
    }

    private UserProfile convertUserProfile(UserProfileEntity entity) {
        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(entity.getEmail());
        userProfile.setName(entity.getName());
        userProfile.setGivenName(entity.getFirstName());
        userProfile.setFamilyName(entity.getLastName());
        userProfile.setExpiryTime(entity.getLastName());
        userProfile.setPicture(entity.getPictureUrl());
        return userProfile;
    }

    public void saveUserProfile(UserProfile userProfile, OAuthToken oAuthToken) {
        UserProfileEntity entity = new UserProfileEntity();
        entity.setName(userProfile.getName());
        entity.setFirstName(userProfile.getGivenName());
        entity.setLastName(userProfile.getFamilyName());
        entity.setEmail(userProfile.getEmail());
        entity.setPictureUrl(userProfile.getPicture());
        entity.setAccessToken(oAuthToken.getAccessToken());
        entity.setRefreshToken(oAuthToken.getRefreshToken());
        entity.setExpiryDate(oAuthToken.getExpiresIn());
        userProfileDao.insert(entity);
    }

    public void clearUserProfile(String email) {
        userProfileDao.deleteUserProfile(email);
    }
}
