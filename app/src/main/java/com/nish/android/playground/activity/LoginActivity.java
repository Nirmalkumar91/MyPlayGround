package com.nish.android.playground.activity;

import android.os.Bundle;

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
    }

    @Override
    protected CompositeDisposable registerEvents() {
        CompositeDisposable events = new CompositeDisposable();
        events.add(eventBus.startActivity(LoginViewModel.class).subscribe(this::startActivity));

        return events;
    }
}
