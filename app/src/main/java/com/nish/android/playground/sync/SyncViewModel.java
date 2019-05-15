package com.nish.android.playground.sync;

import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.landing.LandingActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.discovery.AddressbookHomeProvider;
import com.nish.android.playground.discovery.DavMultiStatus;
import com.nish.android.playground.login.WebLoginUseCase;
import com.nish.android.playground.oauth.DecoderUtil;
import com.nish.android.playground.oauth.OAuthToken;
import com.nish.android.playground.oauth.OAuthTokenProvider;
import com.nish.android.playground.oauth.UserProfile;
import com.nish.android.playground.repository.NishRepository;
import com.nish.android.playground.repository.UserProfileEntity;
import com.nish.android.playground.splash.SplashActivity;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SyncViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;
    private UseCaseDataProvider useCaseDataProvider;
    private DecoderUtil decoderUtil;
    private OAuthTokenProvider oAuthTokenProvider;
    private AddressbookHomeProvider addressbookHomeProvider;
    private NishRepository nishRepository;

    @Inject
    public SyncViewModel(ViewEventBus eventBus, SharedPrefUtil sharedPrefUtil,
                         UseCaseDataProvider useCaseDataProvider, DecoderUtil decoderUtil,
                         OAuthTokenProvider oAuthTokenProvider, AddressbookHomeProvider addressbookHomeProvider,
                         NishRepository nishRepository) {
        this.viewEventBus = eventBus;
        this.sharedPrefUtil = sharedPrefUtil;
        this.useCaseDataProvider = useCaseDataProvider;
        this.decoderUtil = decoderUtil;
        this.oAuthTokenProvider = oAuthTokenProvider;
        this.addressbookHomeProvider = addressbookHomeProvider;
        this.nishRepository = nishRepository;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        WebLoginUseCase loginUseCase = useCaseDataProvider.get(WebLoginUseCase.class);
        String email = sharedPrefUtil.getUserEmail();
        if (loginUseCase == null) {
            String token = nishRepository.getAccessToken(email);
            if (!TextUtils.isEmpty(token)) {
                syncContacts();
            } else {
                sharedPrefUtil.clearEmail();
                launchSplashActivity();
            }
        } else if (!TextUtils.isEmpty(loginUseCase.getAuthCode()))  {
            subscribeOn(oAuthTokenProvider.getAccessToken(loginUseCase.getAuthCode()).subscribe(this::onAuthSuccess, this::onAuthFailure));
        } else {
            sharedPrefUtil.clearEmail();
            launchSplashActivity();
        }
    }

    private void onAuthFailure(Throwable throwable) {
        sharedPrefUtil.clearEmail();
        Log.e("********", "Login failed", throwable);
    }

    private void onAuthSuccess(OAuthToken oAuthToken) {
        try {
            UserProfile userProfile = decoderUtil.decodeIdToken(oAuthToken.getIdToken());
            sharedPrefUtil.setUserEmail(userProfile.getEmail());

            UserProfileEntity userProfileEntity = new UserProfileEntity();
            userProfileEntity.setEmail(userProfile.getEmail());
            userProfileEntity.setAccessToken(oAuthToken.getAccessToken());
            userProfileEntity.setRefreshToken(oAuthToken.getRefreshToken());
            userProfileEntity.setFirstName(userProfile.getGivenName());
            userProfileEntity.setLastName(userProfile.getFamilyName());
            userProfileEntity.setName(userProfile.getName());
            userProfileEntity.setPictureUrl(userProfile.getPicture());
            userProfileEntity.setExpiryDate(0);

            nishRepository.saveUserProfile(userProfileEntity);

            syncContacts();
        } catch (UnsupportedEncodingException e) {
            Log.e("********", e.getMessage(), e);
        }
    }


    private void syncContacts() {
        if(!TextUtils.isEmpty(sharedPrefUtil.getUserEmail())) {
            subscribeOn(addressbookHomeProvider.getAddressbookHome().subscribe(this::onAddressbookHomeSetSuccess, this::onAddressbookHomeSetError));
        }
    }

    private void onAddressbookHomeSetError(Throwable throwable) {
        Log.e("*******", throwable.getMessage(), throwable);
    }

    private void onAddressbookHomeSetSuccess(DavMultiStatus davMultiStatus) {
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getProp().getAddressbookHome().getHref());

        sharedPrefUtil.setAddressbookUrl(davMultiStatus.getResponses().get(0).getPropstat().getProp().getAddressbookHome().getHref());
        subscribeOn(addressbookHomeProvider.getAddressbook().subscribe(this::onAddressbookSuccess, this::onAddressbookError));
    }

    private void onAddressbookError(Throwable throwable) {
        Log.e("*******", throwable.getMessage(), throwable);
    }

    private void onAddressbookSuccess(DavMultiStatus davMultiStatus) {
        Log.e("*******", davMultiStatus.getResponses().get(0).getHref());
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponses().get(0).getPropstat().getProp().getDisplayName());
        Log.e("*******", "is Coll :" + davMultiStatus.getResponses().get(0).getPropstat().getProp().getResourceType().getCollection());
        Log.e("*******", "is AB :" + davMultiStatus.getResponses().get(0).getPropstat().getProp().getResourceType().getAddressbook());
        Log.e("*******", "is Prin :" + davMultiStatus.getResponses().get(0).getPropstat().getProp().getResourceType().getPrincipal());

        Log.e("*******", davMultiStatus.getResponses().get(1).getHref());
        Log.e("*******", davMultiStatus.getResponses().get(1).getPropstat().getStatus());
        Log.e("*******", davMultiStatus.getResponses().get(1).getPropstat().getProp().getDisplayName());
        Log.e("*******", "is Coll :" + davMultiStatus.getResponses().get(1).getPropstat().getProp().getResourceType().getCollection());
        Log.e("*******", "is AB :" + davMultiStatus.getResponses().get(1).getPropstat().getProp().getResourceType().getAddressbook());
        Log.e("*******", "is Prin :" + davMultiStatus.getResponses().get(1).getPropstat().getProp().getResourceType().getPrincipal());
        launchLandingActivity();
    }

    private void launchLandingActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(LandingActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }

    private void launchSplashActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(SplashActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
