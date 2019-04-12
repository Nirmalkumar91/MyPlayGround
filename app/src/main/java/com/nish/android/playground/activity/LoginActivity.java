package com.nish.android.playground.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.databinding.ActivityLoginBinding;
import com.nish.android.playground.viewmodel.LoginViewModel;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    private static final String ERROR_CODE = "error";
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private static final String REDIRECT_URI_ROOT = "com.nish.android.nishcontacts";
    private static final String CODE = "code";

    @Inject
    protected LoginViewModel loginViewModel;

    @Inject
    protected ViewEventBus eventBus;

    @Inject
    protected SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_login);
        loginViewModel.setCallbackEmitter(getCallbackEmitter());
        binding.setViewmodel(loginViewModel);
        setAuthIntent(getIntent());
    }

    @Override
    protected CompositeDisposable registerEvents() {
        CompositeDisposable events = new CompositeDisposable();
        events.add(eventBus.startActivity(LoginViewModel.class).subscribe(this::startActivity));

        return events;
    }

    public void setAuthIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            if (REDIRECT_URI_ROOT.equals(data.getScheme())) {
                String code = data.getQueryParameter(CODE);
                String error = data.getQueryParameter(ERROR_CODE);
                if (!TextUtils.isEmpty(code)) {
                    sharedPrefUtil.setOAuthCode(code);
                }
                if(!TextUtils.isEmpty(error)) {
                    sharedPrefUtil.clearAuthCode();
                }
            }
        }
    }
}
