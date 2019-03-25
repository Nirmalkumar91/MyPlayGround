package com.nish.android.playground.common;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel implements LCCallbackObserver {

    private LCCallbackEmitter callbackEmitter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void subscribeOn(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        compositeDisposable.clear();
    }

    public void setCallbackEmitter(LCCallbackEmitter emitter) {
        this.callbackEmitter = emitter;
        emitter.addObserver(this);
    }
}
