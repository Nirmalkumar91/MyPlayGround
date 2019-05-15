package com.nish.android.playground.landing;

import android.os.Bundle;
import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.databinding.ActivityLandingBinding;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class LandingActivity extends BaseActivity {

    @Inject
    LandingViewModel landingViewModel;

    @Inject
    protected ViewEventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivityLandingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_landing);
        landingViewModel.setCallbackEmitter(getCallbackEmitter());
        binding.setViewmodel(landingViewModel);
    }

    @Override
    protected CompositeDisposable registerEvents() {
        CompositeDisposable events = new CompositeDisposable();
        events.add(eventBus.startActivity(LandingViewModel.class).subscribe(this::startActivity));

        return events;
    }
}
