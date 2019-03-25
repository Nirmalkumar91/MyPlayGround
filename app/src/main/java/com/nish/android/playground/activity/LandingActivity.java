package com.nish.android.playground.activity;

import android.os.Bundle;
import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.databinding.ActivityLandingBinding;
import com.nish.android.playground.viewmodel.LandingViewModel;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;

public class LandingActivity extends BaseActivity {

    @Inject
    LandingViewModel landingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityLandingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_landing);
        landingViewModel.setCallbackEmitter(getCallbackEmitter());
        binding.setViewmodel(landingViewModel);
    }
}
