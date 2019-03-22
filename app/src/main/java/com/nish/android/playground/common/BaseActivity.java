package com.nish.android.playground.common;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private final LCCallbackEmitter callbackEmitter = new LCCallbackEmitter();

    protected LCCallbackEmitter getCallbackEmitter() {
        return callbackEmitter.initialize(getLifecycle());
    }
}
