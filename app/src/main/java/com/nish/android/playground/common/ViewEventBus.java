package com.nish.android.playground.common;

import com.nish.android.playground.common.events.BaseViewEvent;
import com.nish.android.playground.common.events.OpenCustomTabEvent;
import com.nish.android.playground.common.events.StartActivityEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class ViewEventBus {

    private PublishSubject<StartActivityEvent> startActivitySubject = PublishSubject.create();
    private PublishSubject<OpenCustomTabEvent> openCustomTabSubject = PublishSubject.create();

    @Inject
    public ViewEventBus() {
    }

    public void send(StartActivityEvent event) {
        startActivitySubject.onNext(event);
    }

    public void send(OpenCustomTabEvent event) {
        openCustomTabSubject.onNext(event);
    }

    public Observable<StartActivityEvent> startActivity(Object viewModel) {
        return startActivity(viewModel.getClass());
    }

    public Observable<StartActivityEvent> startActivity(Class viewModelClass) {
        return startActivitySubject.filter(event -> fromEmitter(event, viewModelClass));
    }

    public Observable<OpenCustomTabEvent> openCustomTab(Class viewModelClass) {
        return openCustomTabSubject.filter(event -> fromEmitter(event, viewModelClass));
    }

    private boolean fromEmitter(BaseViewEvent event, Class viewModelClass) {
        return viewModelClass.getName().equals(event.getEmitter().getClass().getName());
    }
}
