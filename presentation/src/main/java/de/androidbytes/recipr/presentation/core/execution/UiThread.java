package de.androidbytes.recipr.presentation.core.execution;

import de.androidbytes.recipr.domain.executor.PostExecutionThread;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Christoph on 02.05.2016.
 */
@Singleton
public class UiThread implements PostExecutionThread {

    @Inject
    public UiThread() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
