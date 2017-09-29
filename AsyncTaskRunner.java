package com.tencent.jungle.message.util;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import org.apache.commons.configuration.Configuration;

import java.util.concurrent.*;

/**
 * 异步runner
 */
@Singleton
public class AsyncTaskRunner {
    private ExecutorService service;

    @Inject
    public AsyncTaskRunner(Injector injector) {
        Configuration configuration = injector.getInstance(Configuration.class);
        int threads = configuration.getInt("async.threads", 12);
        int queueSize = configuration.getInt("async.queue", 2048);
        service = new ThreadPoolExecutor(threads, threads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(queueSize));

    }

    public Future<?> submit(Runnable task) {
        try {
            return this.service.submit(task);
        } catch (Exception e) {
            return null;
        }
    }
}
