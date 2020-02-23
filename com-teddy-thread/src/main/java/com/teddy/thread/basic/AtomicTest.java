package com.teddy.thread.basic;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * package com.teddy.thread
 * description: 原子性
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 11:51
 */
public class AtomicTest {

    static class AddTest{
        volatile static int count;
        static void unsafeAddCount(){
            for (int i = 0; i < 100; i++) {
                count++;
            }
            System.out.println("count = " + count);
        }
        synchronized static void safeAddCount(){
            for (int i = 0; i < 100; i++) {
                count++;
            }
            System.out.println("count = " + count);
        }
    }

    @Test
    /**
     * volatile只能保证可见性，并不能保证原子性，表达式i++操作步骤分解如下：
     *
     * 1、从内存找那个取出i的值 A-->100         B --> 101
     * 2、计算i的值      A-->101
     *      100 == 101
     *      A --> 101
     *      A --> 102
     *      101 == 101
     * 3、将i的值写到内存中
     * 加入在第二步另外一个线程也修改i的值，就会出现脏数据。
     */
    public void unsafeAddCountTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> AddTest.unsafeAddCount());
            thread.start();
        }
        Thread.sleep(1000);
    }

    @Test
    /**
     * 使用synchronized即可保证i++的原子性
     */
    public void safeAddCountTest() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> AddTest.safeAddCount());
            thread.start();
        }
        Thread.sleep(1000);
    }

    static class SafeAddCount{
        AtomicInteger count = new AtomicInteger(0);
        void safeAddCount(){
            for (int i = 0; i < 100; i++) {
                count.incrementAndGet();
            }
            System.out.println("count = " + count.get());
        }
    }

    @Test
    /**
     * AtomicInteger 采用cas自旋保证线程安全
     */
    public void atomicIntegerSafeTest() throws InterruptedException {
        SafeAddCount safeAddCount = new SafeAddCount();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> safeAddCount.safeAddCount());
            thread.start();
        }
        Thread.sleep(1000);
    }
}
