package com.nish.android.playground.activity;

import android.os.Bundle;

import com.nish.android.playground.R;
import com.nish.android.playground.common.BaseActivity;
import com.nish.android.playground.common.ViewEventBus;
import com.nish.android.playground.databinding.ActivitySyncBinding;
import com.nish.android.playground.viewmodel.LoginViewModel;
import com.nish.android.playground.viewmodel.SyncViewModel;

import javax.inject.Inject;

import androidx.databinding.DataBindingUtil;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class SyncActivity extends BaseActivity {

    @Inject
    protected SyncViewModel syncViewModel;

    @Inject
    protected ViewEventBus eventBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        ActivitySyncBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_sync);
        syncViewModel.setCallbackEmitter(getCallbackEmitter());
        binding.setViewmodel(syncViewModel);
    }

    @Override
    protected CompositeDisposable registerEvents() {
        CompositeDisposable events = new CompositeDisposable();
        events.add(eventBus.startActivity(SyncViewModel.class).subscribe(this::startActivity));

        return events;
    }
}
