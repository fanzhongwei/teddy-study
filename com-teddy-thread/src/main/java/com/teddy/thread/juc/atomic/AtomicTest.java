package com.teddy.thread.juc.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import sun.misc.Unsafe;

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
        volatile static int i;
        static void unsafeAddCount(){
            i++;
        }
        /**
         * 使用synchronized即可保证i++的原子性
         */
        synchronized static void safeAddCount(){
            i++;
        }
    }

    /**
     * volatile只能保证可见性，并不能保证原子性，表达式i++操作步骤分解如下：<br/>
     *
     * 1、从内存取出i的值放到线程栈<br/>
     * 2、在线程栈中计算i+1的值<br/>
     * 3、将i+1的值写到内存中的变量i<br/>
     *
     * 很不幸的是，这几个操作并不是原子性的，如果多个同时进行i++操作，就会出现线程安全问题。<br/>
     * 1、获取--> 线程A：i=1，线程B：i=1<br/>
     * 2、计算--> 线程A：i+1=2，线程B：i+1=2<br/>
     * 3、回写--> 线程A：i=2，线程B：i=2<br/>
     */
    @Test
    public void unsafeAddCountTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> AddTest.unsafeAddCount());
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println("i = " + AddTest.i);
    }

    @Test
    public void safeAddCountTest() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> AddTest.safeAddCount());
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println("i = " + AddTest.i);
    }

    @Test
    /**
     * AtomicInteger 采用cas自旋保证线程安全
     */
    public void atomicIntegerSafeTest() throws InterruptedException {
        AtomicInteger i = new AtomicInteger();
        for (int j = 0; j < 100; j++) {
            Thread thread = new Thread(() -> i.getAndIncrement());
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println("i = " + i.get());
    }
}
