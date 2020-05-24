package com.teddy.thread.juc.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * package com.teddy.thread
 * description: 线程池工具类
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 14:20
 */
public class ThreadPoolUtils {
    /**
     * 任务等待队列 容量
     */
    private static final int TASK_QUEUE_SIZE = 1000;
    /**
     * 空闲线程存活时间 单位分钟
     */
    private static final long KEEP_ALIVE_TIME = 10L;

    /**
     * 任务执行线程池
     */
    private static ThreadPoolExecutor threadPool;

    static {
        int corePoolNum = 2 * Runtime.getRuntime().availableProcessors() + 1;
        int maximumPoolSize = 2 * corePoolNum;
        threadPool = new ThreadPoolExecutor(
                corePoolNum,
                maximumPoolSize,
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(TASK_QUEUE_SIZE),
                new ThreadFactoryBuilder().setNameFormat("ThreadPoolUtils-%d").build(),
                (r, executor) -> {
                    if (!executor.isShutdown()) {
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                });
    }

    /**
     * Description: 执行任务
     *
     * @param task 任务
     * @author teddy
     * @date 2018/9/16
     */
    public static void execute(Runnable task) {
        threadPool.execute(task);
    }

    /**
     * Description: 提交任务到线程池
     *
     * @param task 任务
     *
     * @return Future<T>
     * @author teddy
     * @date 2019/3/23
     */
    public static <T> Future<T> submit(Callable<T> task){
        return threadPool.submit(task);
    }
}
