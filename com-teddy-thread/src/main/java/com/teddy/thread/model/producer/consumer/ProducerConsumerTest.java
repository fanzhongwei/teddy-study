package com.teddy.thread.model.producer.consumer;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ProducerConsumerTest {

    @Test
    public void test() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(100);
        for (int i = 0; i < 10; i++) {
            new Producer(queue).start();
        }

        for (int i = 0; i < 20; i++) {
            new Consumer(queue).start();
        }

        Thread.sleep(100000);
    }

    @Test
    public void test1() throws InterruptedException {
        BlockingQueue<String> queue = new SynchronousQueue<>();
        for (int i = 0; i < 1; i++) {
            new Producer(queue).start();
        }

        for (int i = 0; i < 1; i++) {
            new Consumer(queue).start();
        }

        Thread.sleep(100000);
    }
}
