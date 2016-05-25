package de.androidbytes.recipr.domain;

import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import de.androidbytes.recipr.domain.executor.ExecutionThread;
import de.androidbytes.recipr.domain.interactor.core.BaseUseCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

public class UseCaseTest {

    @Mock
    private ExecutionThread mockExecutionThread;

    @Mock
    private PostExecutionThread mockPostExecutionThread;

    private UseCaseTestClass testUseCase;
    private TestSubscriber subscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        subscriber = new TestSubscriber();
    }

    @Test
    public void Execute_SubscriberAfterExecution_SubscriberIsSubscribed() {
        givenDelayedTestObservable();
        testUseCase.execute(subscriber);
        assertThat(subscriber.isUnsubscribed(), is(false));
    }

    private void givenDelayedTestObservable() {
        TestScheduler scheduler = new TestScheduler();
        given(mockPostExecutionThread.getScheduler()).willReturn(scheduler);
        this.testUseCase = new UseCaseTestClass(
                Observable.timer(1, TimeUnit.MILLISECONDS),
                mockExecutionThread,
                mockPostExecutionThread
        );
    }

    @Test
    public void Execute_UnsubscribedSubscriberAfterExecution_SubscriberIsUnsubscribed() {
        givenEmptyObservable();
        testUseCase.execute(subscriber);
        subscriber.unsubscribe();
        assertThat(subscriber.isUnsubscribed(), is(true));
    }

    private void givenEmptyObservable() {
        this.testUseCase = new UseCaseTestClass(
                Observable.empty(),
                mockExecutionThread,
                mockPostExecutionThread
        );
    }

    private static class UseCaseTestClass extends BaseUseCase {

        private Observable observable;

        protected UseCaseTestClass(Observable observable, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
            super(executionThread, postExecutionThread);
            this.observable = observable;
        }

        @Override
        protected Observable buildUseCaseObservable() {
            return observable;
        }
    }

}
