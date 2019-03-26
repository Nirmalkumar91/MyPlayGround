package com.nish.android.playground.activity;

import android.os.Bundle;
import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.viewmodel.SplashViewModel;
import com.nish.android.playground.databinding.ActivitySplashBinding;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class SplashActivity extends BaseActivity {

    @Inject
    protected SplashViewModel splashViewModel;

    @Inject
    protected ViewEventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_splash);
        splashViewModel.setCallbackEmitter(getCallbackEmitter());
        binding.setViewmodel(splashViewModel);
    }

    @Override
    protected CompositeDisposable registerEvents() {
        CompositeDisposable events = new CompositeDisposable();
        events.add(eventBus.startActivity(SplashViewModel.class).subscribe(this::startActivity));

        return events;
    }
}
