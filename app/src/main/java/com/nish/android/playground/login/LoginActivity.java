package com.nish.android.playground.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.nish.android.playground.AppConstants;
import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.common.SharedPrefUtil;
import com.nish.android.playground.common.UseCaseDataProvider;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.databinding.ActivityLoginBinding;
import com.nish.android.playground.sync.SyncActivity;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends BaseActivity {

    @Inject
    protected LoginViewModel loginViewModel;

    @Inject
    protected ViewEventBus eventBus;

    @Inject
    protected UseCaseDataProvider useCaseDataProvider;

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
        events.add(eventBus.openCustomTab(LoginViewModel.class).subscribe(this::openCustomTab));

        return events;
    }

    private void setAuthIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            if (AppConstants.REDIRECT_URI_ROOT.equals(data.getScheme())) {
                String code = data.getQueryParameter(AppConstants.CODE);
                String error = data.getQueryParameter(AppConstants.ERROR_CODE);
                useCaseDataProvider.save(new WebLoginUseCase(code, error));

                Intent startIntent = new Intent(this, SyncActivity.class);
                startActivity(startIntent);
                finish();
            }
        }
    }
}
