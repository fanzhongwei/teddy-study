package com.teddy.thread.basic;

import java.util.concurrent.*;

import com.teddy.thread.pool.ThreadPoolUtils;
import org.junit.Test;

/**
 * package com.teddy.thread
 * description: 线程池
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 14:00
 */
public class ThreadPoolTest {

    private void execute(String index) {
        System.out.println("ThreadName = " + Thread.currentThread().getName() + " 任务--" + index + "开始执行... time = " + System.currentTimeMillis());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("ThreadName = " + Thread.currentThread().getName() + " 任务--" + index + "执行完毕... time = " + System.currentTimeMillis());
    }

    @Test
    public void fixedThreadPoolTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 12; i++) {
            final String index = String.valueOf(i);
            executorService.execute(()-> execute(index));
        }
        Thread.sleep(5000);
    }

    @Test
    public void cachedThreadPoolTest() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; i++) {
            final String index = String.valueOf(i);
            executorService.execute(()-> execute(index));
        }
        Thread.sleep(5000);
    }

    @Test
    public void singleThreadPooleTest() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 12; i++) {
            final String index = String.valueOf(i);
            executorService.execute(()-> execute(index));
        }
        Thread.sleep(5000);
    }

    @Test
    public void scheduledThreadPoolTest() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        for (int i = 0; i < 12; i++) {
            final String index = String.valueOf(i);
            scheduledExecutorService.schedule(()-> execute(index),1000, TimeUnit.MILLISECONDS);
            System.out.println(" 任务--" + index + "已提交... time = " + System.currentTimeMillis());
        }
        Thread.sleep(15000);
    }

    @Test
    public void notBlockingThreadPoolTest() throws InterruptedException {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                4,
                4,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10)
        );
        for (int i = 0; i < 15; i++) {
            final String index = String.valueOf(i);
            pool.execute(()->execute(index));
        }
        Thread.sleep(5000);
    }
}
