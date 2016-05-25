package de.androidbytes.recipr.domain.interactor.core;

import rx.Subscriber;

public interface UseCase {

    /**
     * Execute the current {@link UseCase} implementation.
     * @param subscriber The {@link Subscriber} called on certain events [onNext(), onError(), onCompleted()]
     */
    void execute(Subscriber subscriber);

    /**
     * Unsubscribe from the current {@link rx.Subscription}.
     */
    void unsubscribe();

}
