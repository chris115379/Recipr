package de.androidbytes.recipr.presentation.core.execution;

import de.androidbytes.recipr.domain.executor.ExecutionThread;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Christoph on 02.05.2016.
 */
@Singleton
public class WorkerThread implements ExecutionThread {

    @Inject
    public WorkerThread() {}

    @Override
    public Scheduler getScheduler() {
        return Schedulers.newThread();
    }
}
