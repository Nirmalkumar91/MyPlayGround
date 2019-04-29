package com.nish.android.playground.login;

import android.os.Bundle;

import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.databinding.ActivityWebLoginBinding;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class WebLoginActivity extends BaseActivity {

    @Inject
    protected WebLoginViewModel webLoginViewModel;

    @Inject
    protected ViewEventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityWebLoginBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_web_login);
        webLoginViewModel.setCallbackEmitter(getCallbackEmitter());
        binding.setViewmodel(webLoginViewModel);
    }

    @Override
    protected CompositeDisposable registerEvents() {
        CompositeDisposable events = new CompositeDisposable();
        events.add(eventBus.startActivity(WebLoginViewModel.class).subscribe(this::startActivity));

        return events;
    }
}
