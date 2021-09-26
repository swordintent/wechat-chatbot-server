package com.swordintent.wx.mp.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 连接池工具类
 *
 * @author liuhe
 */
@Component
public class ExecutorHelper {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorHelper.class);

    private ThreadPoolExecutor threadPoolExecutor;

    private final int threadNum = 80;

    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void init() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(threadNum, threadNum,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(threadNum * 4),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(() -> {
                logger.info(String.format("ExecutorHelper common executor -- %s", threadPoolExecutor));
            }, 0, 30, TimeUnit.SECONDS);
            logger.info(String.format("ExecutorHelper common executor inited -- %s", threadPoolExecutor));
        }
    }

    public <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, threadPoolExecutor);
    }

    public CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, threadPoolExecutor);
    }

    @PreDestroy
    public void shutdown() {
        try {
            if (threadPoolExecutor != null) {
                threadPoolExecutor.shutdown();
            }
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown();
            }
        } catch (Exception e) {
            logger.error("ExecutorHelper common executor shutdown error", e);
        }
    }
}
