package com.teddy.thread.safe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

/**
 * package com.teddy.thread
 * description:
 *
 * 线程安全集合
 * Vector
 * HashTable
 * StringBuffer
 *
 * 非线程安全集合
 * ArrayList
 * LinkedList
 * HashMap
 * HashSet
 * TreeMap
 * TreeSet
 * StringBulider
 *
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 13:09
 */
public class ThreadSafeCollection {

    private void addStringIntoList(List<String> list){
        for (int i = 0; i < 100; i++) {
            list.add(i + "");
        }
        System.out.println("list.size() = " + list.size());
    }
    @Test
    public void arrayListIsUnsafeTest() throws InterruptedException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(()-> addStringIntoList(list));
            thread.start();
        }
        Thread.sleep(100);
    }

    @Test
    /**
     * 要使用线程安全的集合可以使用Collections中的
     * Collections.synchronizedList
     * Collections.synchronizedMap
     * Collections.synchronizedSet
     *
     * ConcurrentHashMap也是线程安全的
     */
    public void syncListTest() throws InterruptedException {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(()-> addStringIntoList(list));
            thread.start();
        }
        Thread.sleep(100);
    }

    @Test
    /**
     * 阻塞队列：BlockingQueue
     * {@link ArrayBlockingQueue} 有界队列
     * {@link java.util.concurrent.LinkedBlockingQueue} 单向并发阻塞队列({@link Integer#MAX_VALUE}) FIFO
     * {@link java.util.concurrent.LinkedBlockingDeque} 双向并发阻塞队列({@link Integer#MAX_VALUE}) FIFO、FILO
     */
    public void arrayBlockingQueueTest() throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue(10);
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    System.out.println("出队 i = " + queue.take() + " time = " + System.currentTimeMillis());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        for (int i = 0; i < 20; i++) {
            queue.put("" + i);
            System.out.println("进队 i = " + i + " time = " + System.currentTimeMillis());
        }
        Thread.sleep(11000);
    }
}
