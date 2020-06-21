package com.teddy.thread.model.producer.consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer extends Thread {
    private BlockingQueue<String> queue;
    public Producer(BlockingQueue queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 给个随机数，表示生产者生产的耗时
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                // 添加到缓冲队列
                String product = "Producer: " + Thread.currentThread().getName() + " make data: " + System.currentTimeMillis();
                queue.put(product);
                System.out.println(product);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
