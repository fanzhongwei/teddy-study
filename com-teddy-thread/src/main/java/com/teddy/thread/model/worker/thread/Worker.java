package com.teddy.thread.model.worker.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Worker extends Thread{

    private BlockingQueue<String> prevChannel;
    private BlockingQueue<String> nextChannel;
    private String work;
    private int useTime;

    public Worker(String work, int useTime, BlockingQueue<String> prevChannel, BlockingQueue<String> nextChannel){
        this.work = work;
        this.prevChannel = prevChannel;
        this.useTime = useTime;
        this.nextChannel = nextChannel;
    }

    @Override
    public void run() {
        while (true) {
            // 给个随机数，表示工作者的耗时
            try {
                String order = prevChannel.take();
                TimeUnit.SECONDS.sleep(useTime);
                // 添加到channel
                order = String.format("%s%n order step:%s by worker[%s] use %ds", order, work, Thread.currentThread().getName(), useTime);

                if (null != nextChannel) {
                    nextChannel.put(order);
                } else {
                    System.out.println(order);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
