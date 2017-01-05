package com.biz.stratadigm.tpi.manager;

import rx.Scheduler;

public class AppSchedulers {
    private final Scheduler mainThreadScheduler;
    private final Scheduler ioScheduler;

    public AppSchedulers(Scheduler mainThreadScheduler, Scheduler ioScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioScheduler = ioScheduler;
    }

    public Scheduler mainThread() {
        return mainThreadScheduler;
    }

    public Scheduler io() {
        return ioScheduler;
    }
}
