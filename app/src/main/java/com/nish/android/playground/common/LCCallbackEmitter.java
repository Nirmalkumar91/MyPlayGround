package com.nish.android.playground.common;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Lifecycle;

public class LCCallbackEmitter {

    private Lifecycle lifecycle;
    private List<LCCallbackObserver> observers = new ArrayList<>();

    public LCCallbackEmitter initialize(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
        return this;
    }

    public void addObserver(LCCallbackObserver observer) {
        observers.add(observer);
        lifecycle.addObserver(observer);
    }
}
