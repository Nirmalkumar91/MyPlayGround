package com.nish.android.playground.oauth;

import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.userdb.UserProfileDatabase;

import javax.inject.Inject;

import io.reactivex.Observable;

public class UserProfileRepository {

    private OAuthTokenProvider oAuthTokenProvider;
    private UserProfileDatabase userProfileDatabase;
    private SharedPrefUtil sharedPrefUtil;
    private DecoderUtil decoderUtil;

    @Inject
    public UserProfileRepository(OAuthTokenProvider oAuthTokenProvider,
                                 UserProfileDatabase userProfileDatabase,
                                 SharedPrefUtil sharedPrefUtil, DecoderUtil decoderUtil) {
        this.oAuthTokenProvider = oAuthTokenProvider;
        this.userProfileDatabase = userProfileDatabase;
        this.sharedPrefUtil = sharedPrefUtil;
        this.decoderUtil = decoderUtil;
    }

    private Observable<UserProfile> userAuthObservable = Observable.defer(
            () -> oAuthTokenProvider.getAccessToken(sharedPrefUtil.getOAuthCode()).map(
                    oAuthToken -> {
                        UserProfile userProfile = decoderUtil.decodeIdToken(oAuthToken.getIdToken());
                        sharedPrefUtil.setUserEmail(userProfile.getEmail());
                        userProfileDatabase.saveUserProfile(userProfile, oAuthToken);
                        return userProfile;
                    }));

    public Observable<UserProfile> getUserProfile() {
        return userAuthObservable;
    }
}
