package com.nish.android.playground.viewmodel;

import com.nish.android.playground.activity.WebLoginActivity;
import com.nish.android.playground.common.BaseViewModel;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.common.events.StartActivityEvent;
import com.nish.android.playground.oauth.OAuthTokenProvider;

import javax.inject.Inject;

import androidx.databinding.ObservableField;

public class LoginViewModel extends BaseViewModel {

    public ObservableField<String> emailId = new ObservableField<>("dare.lamrin@googlemail.com");
    private ViewEventBus viewEventBus;
    private SharedPrefUtil sharedPrefUtil;
    private OAuthTokenProvider oAuthTokenProvider;

    @Inject
    public LoginViewModel(ViewEventBus eventBus, SharedPrefUtil sharedPrefUtil, OAuthTokenProvider oAuthTokenProvider) {
        this.viewEventBus = eventBus;
        this.sharedPrefUtil = sharedPrefUtil;
        this.oAuthTokenProvider = oAuthTokenProvider;
    }

    public void launchGoogleLogin() {
        sharedPrefUtil.setUserEmail(emailId.get());
        StartActivityEvent event = StartActivityEvent.getEventBuilder(this)
                .setActivity(WebLoginActivity.class)
                .setFinishActivity(true)
                .build();

        viewEventBus.send(event);
    }
}
