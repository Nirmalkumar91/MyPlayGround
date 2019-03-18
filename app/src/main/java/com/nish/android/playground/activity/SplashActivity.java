package com.nish.android.playground.activity;

import android.os.Bundle;
import com.nish.android.playground.R;
import com.nish.android.playground.viewmodel.SplashViewModel;
import com.nish.android.playground.databinding.ActivitySplashBinding;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;

public class SplashActivity extends AppCompatActivity {

    @Inject
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivitySplashBinding activitySplashBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_splash);
        activitySplashBinding.setViewmodel(splashViewModel);
    }
}
