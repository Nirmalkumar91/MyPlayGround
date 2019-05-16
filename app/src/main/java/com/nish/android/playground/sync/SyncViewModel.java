package com.nish.android.playground.sync;

import android.text.TextUtils;
import android.util.Log;

import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.landing.LandingActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.addressbook.AddressbookHomeProvider;
import com.nish.android.playground.addressbook.DavMultiStatus;
import com.nish.android.playground.login.WebLoginUseCase;
import com.nish.android.playground.oauth.UserProfileRepository;
import com.nish.android.playground.oauth.UserProfile;
import com.nish.android.playground.userdb.UserProfileDatabase;
import com.nish.android.playground.splash.SplashActivity;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

public class SyncViewModel extends BaseViewModel {

    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;
    private UseCaseDataProvider useCaseDataProvider;
    private UserProfileRepository userProfileRepository;
    private AddressbookHomeProvider addressbookHomeProvider;
    private UserProfileDatabase userProfileDatabase;
    private String email;

    @Inject
    public SyncViewModel(ViewEventBus eventBus, SharedPrefUtil sharedPrefUtil,
                         UseCaseDataProvider useCaseDataProvider,
                         UserProfileRepository userProfileRepository,
                         AddressbookHomeProvider addressbookHomeProvider,
                         UserProfileDatabase userProfileDatabase) {
        this.viewEventBus = eventBus;
        this.sharedPrefUtil = sharedPrefUtil;
        this.useCaseDataProvider = useCaseDataProvider;
        this.userProfileRepository = userProfileRepository;
        this.addressbookHomeProvider = addressbookHomeProvider;
        this.userProfileDatabase = userProfileDatabase;
        this.email = sharedPrefUtil.getUserEmail();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        WebLoginUseCase loginUseCase = useCaseDataProvider.get(WebLoginUseCase.class);
        if (loginUseCase == null) {
            String token = userProfileDatabase.getAccessToken(email);
            if (!TextUtils.isEmpty(token)) {
                syncContacts();
            } else {
                userProfileDatabase.clearUserProfile(email);
                sharedPrefUtil.clearEmail();
                launchSplashActivity();
            }
        } else if (!TextUtils.isEmpty(loginUseCase.getAuthCode()))  {
            sharedPrefUtil.setOAuthCode(loginUseCase.getAuthCode());
            subscribeOn(userProfileRepository.getUserProfile().subscribe(this::onAuthSuccess, this::onAuthFailure));
        } else {
            sharedPrefUtil.clearEmail();
            launchSplashActivity();
        }
    }

    private void onAuthFailure(Throwable throwable) {
        sharedPrefUtil.clearEmail();
        Log.e("********", "Login failed", throwable);
    }

    private void onAuthSuccess(UserProfile userProfile) {
        email = userProfile.getEmail();
        UserProfile profile = userProfileDatabase.getUserProfile(email);
        Log.e("********", "Auth Success: " + profile.getEmail());
        Log.e("********", "Name: " + profile.getName());
        Log.e("********", "Picture: " + profile.getPicture());
        syncContacts();
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
