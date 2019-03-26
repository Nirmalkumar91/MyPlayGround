package com.nish.android.playground.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.nish.android.playground.activity.LandingActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;

import javax.inject.Inject;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import okhttp3.HttpUrl;

public class LoginViewModel extends BaseViewModel {

    private static final String CLIENT_ID = "580105838297-sdsdedr0t7ijdmrktdkc9ov66rsvdd9a.apps.googleusercontent.com";
    private static final String REDIRECT_URI = "com.nish.android.nishcontacts:/oauth2redirect";
    private static final String CODE = "code";
    private static final String API_SCOPE = "https://www.googleapis.com/auth/drive";

    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;

    @Inject
    public LoginViewModel(ViewEventBus eventBus, SharedPrefUtil sharedPrefUtil) {
        this.viewEventBus = eventBus;
        this.sharedPrefUtil = sharedPrefUtil;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if(!TextUtils.isEmpty(sharedPrefUtil.getOAuthToken())) {
            launchLandingActivity();
        }
    }

    public void launchGoogleLogin() {
        StartActivityEvent startActivityEvent = StartActivityEvent.getEventBuilder(this)
                .isExternalApp(true)
                .setIntent(getAuthorizationIntent())
                .build();

        viewEventBus.send(startActivityEvent);
    }

    private Intent getAuthorizationIntent() {
        HttpUrl authorizeUrl = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth") //
                .newBuilder() //
                .addQueryParameter("client_id", CLIENT_ID)
                .addQueryParameter("scope", API_SCOPE)
                .addQueryParameter("redirect_uri", REDIRECT_URI)
                .addQueryParameter("response_type", CODE)
                .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    private void launchLandingActivity() {
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(LandingActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
