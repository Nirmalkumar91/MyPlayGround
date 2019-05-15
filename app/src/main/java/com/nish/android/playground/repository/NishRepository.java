package com.nish.android.playground.repository;

import com.nish.android.playground.oauth.UserProfile;

import javax.inject.Inject;

public class NishRepository {

    private UserProfileDao userProfileDao;

    @Inject
    public NishRepository(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public String getAccessToken(String email) {
        return userProfileDao.getAccessToken(email);
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

    public void saveUserProfile(UserProfileEntity userProfileEntity) {
        userProfileDao.insert(userProfileEntity);
    }

    public void clearUserProfile(String email) {
        userProfileDao.deleteUserProfile(email);
    }
}
