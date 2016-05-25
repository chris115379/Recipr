package de.androidbytes.recipr.domain.interactor.core;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func0;
import rx.subscriptions.Subscriptions;

public abstract class BaseUseCase<T> implements UseCase {

    private final ExecutionThread executionThread;
    private final PostExecutionThread postExecutionThread;
    private Subscription subscription = Subscriptions.empty();

    protected BaseUseCase(ExecutionThread executionThread,
                          PostExecutionThread postExecutionThread) {
        this.executionThread = executionThread;
        this.postExecutionThread = postExecutionThread;
    }


    protected abstract Observable<T> buildUseCaseObservable();

    @SuppressWarnings("unchecked")
    public void execute(Subscriber subscriber) {
        subscription = deferredObservable()
                .subscribeOn(executionThread.getScheduler())
                .observeOn(postExecutionThread.getScheduler())
                .subscribe(subscriber);
    }

    private Observable deferredObservable() {
        return Observable.defer(new Func0<Observable<T>>() {
            @Override
            public Observable<T> call() {
                return buildUseCaseObservable();
            }
        });
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
