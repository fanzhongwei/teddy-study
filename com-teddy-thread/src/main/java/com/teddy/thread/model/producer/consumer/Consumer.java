package com.teddy.thread.model.producer.consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer extends Thread {
    private BlockingQueue<String> queue;
    public Consumer(BlockingQueue queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // 从缓冲队列中获取数据
                String product = queue.take();
                System.out.println("Consumer: " + Thread.currentThread().getName() + " consume:" + product);
                // 给个随机数，表示消费者消费的耗时
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
