package com.nish.android.playground.common;

import android.content.Intent;

import com.nish.android.playground.common.events.StartActivityEvent;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity extends AppCompatActivity {

    private final LCCallbackEmitter callbackEmitter = new LCCallbackEmitter();
    private CompositeDisposable lifeCycleSubscription = new CompositeDisposable();

    protected CompositeDisposable registerEvents() {
        return null;
    }

    @Override
    protected void onResume() {
        CompositeDisposable disposable = registerEvents();
        if(disposable != null) {
            lifeCycleSubscription.add(disposable);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        lifeCycleSubscription.clear();
        super.onPause();
    }

    protected LCCallbackEmitter getCallbackEmitter() {
        return callbackEmitter.initialize(getLifecycle());
    }

    protected void startActivity(StartActivityEvent event) {
        if(event.isExternalApp() && event.getIntent() != null) {
            startActivity(event.getIntent());
            finish();
        } else {
            Intent startIntent = new Intent(this, event.getActivityClass());
            startActivity(startIntent);
            if (event.finishActivity()) {
                finish();
            }
        }
    }
}
