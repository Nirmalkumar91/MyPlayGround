package com.nish.android.playground.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

@Singleton
public class UseCaseDataProvider {

    private ConcurrentMap<Class, UseCase> useCaseData = new ConcurrentHashMap();
    private PublishSubject subject = PublishSubject.create();

    @Inject
    public UseCaseDataProvider() {}

    public void save(UseCase useCase) {
        final Class key = useCase.getClass();
        useCaseData.put(key, useCase);
        subject.onNext(key);
    }

    public <T extends UseCase> T get(Class<T> useCaseClass) {
        final UseCase useCase = useCaseData.remove(useCaseClass);

        return useCaseClass.cast(useCase);
    }

    public Observable<Class> observeUseCase(Class useCaseClass) {
        return subject.hide().filter(key -> key.equals(useCaseClass));
    }
}
