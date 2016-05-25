package de.androidbytes.recipr.domain.interactor.core;

import rx.Subscriber;

/**
 * Created by Christoph on 07.05.2016.
 */
public class DefaultSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {

    }
}
