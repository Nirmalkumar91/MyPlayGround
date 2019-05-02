package com.nish.android.playground.common;

import android.content.Intent;
import android.net.Uri;

import com.nish.android.playground.common.events.OpenCustomTabEvent;
import com.nish.android.playground.common.events.StartActivityEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
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

    protected void openCustomTab(OpenCustomTabEvent event) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(event.getUrl()));
    }
}
