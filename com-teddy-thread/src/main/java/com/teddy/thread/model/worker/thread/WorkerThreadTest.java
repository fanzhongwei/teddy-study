package com.teddy.thread.model.worker.thread;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WorkerThreadTest {

    @Test
    public void test() throws InterruptedException {
        BlockingQueue<String> order = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> channel1 = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> channel2 = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> channel3 = new ArrayBlockingQueue<>(100);


        for (int i = 0; i < 2; i++) {
            Worker worker = new Worker("洗菜", 2, order, channel1);
            worker.setName("洗菜员" + i);
            worker.start();
        }

        for (int i = 0; i < 4; i++) {
            Worker worker = new Worker("切菜", 4, channel1, channel2);
            worker.setName("切菜员" + i);
            worker.start();
        }

        for (int i = 0; i < 8; i++) {
            Worker worker = new Worker("炒菜", 6, channel2, channel3);
            worker.setName("炒菜员" + i);
            worker.start();
        }

        for (int i = 0; i < 2; i++) {
            Worker worker = new Worker("上菜", 1, channel3, null);
            worker.setName("上菜员" + i);
            worker.start();
        }

        int i = 1;
        while(true) {
            order.put("点菜，菜品ID=" + i);
        }
    }
}
