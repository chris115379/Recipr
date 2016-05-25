package de.androidbytes.recipr.domain.executor;

import rx.Scheduler;

/**
 * Created by Christoph on 01.05.2016.
 */
public interface ExecutionScheduler {
    Scheduler getScheduler();
}
