package com.teddy.thread.juc.pool;

import java.util.concurrent.*;

import org.junit.Test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author fanzhongwei
 */
@Slf4j
public class ExecutorsTest {

    private static class CustomThreadPoolExecutor extends ThreadPoolExecutor{

        public CustomThreadPoolExecutor(int corePoolSize,
                                        int maximumPoolSize,
                                        long keepAliveTime,
                                        TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue,
                                        ThreadFactory threadFactory,
                                        RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r){
            log.info("线程：{}，即将执行任务...", t.getName());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t){
            log.info("线程：{}，即将执行任务...", Thread.currentThread().getName());
        }

        @Override
        protected void terminated() {
            log.info("线程池已经终止");
        }
    }

    @Test
    public void executTest() throws InterruptedException {
        CustomThreadPoolExecutor pool  = new CustomThreadPoolExecutor(
                2,
                2,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadFactoryBuilder().setNameFormat("CustomThreadPool-%d").build(),
                (r, executor) -> {
                    if (!executor.isShutdown()) {
                        try {
                            log.info("线程池已满，缓冲队列也满了，任务将排队阻塞...");
                            executor.getQueue().put(r);
                            log.info("线程池空闲了，任务已添加到队列了，等待执行...");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        for (int i = 0; i < 6 ; i++){
            final int index = i;
            pool.execute(()->{
                log.info("任务：{}，执行中...", index);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            });
        }

        while(true){
            if(pool.getActiveCount() > 0){
                Thread.sleep(1000);
            }else{
                pool.shutdown();
                Thread.sleep(1000);
                break;
            }
        }
    }
}
