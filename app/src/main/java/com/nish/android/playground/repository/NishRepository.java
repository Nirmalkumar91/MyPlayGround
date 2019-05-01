package com.nish.android.playground.repository;

import javax.inject.Inject;

import io.reactivex.Single;

public class NishRepository {

    private UserProfileDao userProfileDao;

    @Inject
    public NishRepository(UserProfileDao userProfileDao) {
        this.userProfileDao = userProfileDao;
    }

    public Single<UserProfileEntity> getUserProfile(String email) {
        return userProfileDao.getUserProfile(email);
    }

    public void saveUserProfile(UserProfileEntity userProfileEntity) {
        userProfileDao.insert(userProfileEntity);
    }
}
