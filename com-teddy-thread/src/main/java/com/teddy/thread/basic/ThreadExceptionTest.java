package com.teddy.thread.basic;

import org.junit.Test;

public class ThreadExceptionTest {

    /**
     * 异常一旦被Thread.run() 抛出后，就不能在程序中对异常进行捕获，最终只能由JVM捕获
     */
    @Test
    public void test_thread_exception() {
        new Thread(() -> {int a = 1/0;}).start();
    }

    /**
     * 尝试对线程中抛出的异常进行捕获，但是无济于事，不信你试试
     */
    @Test
    public void test_catch_thread_exception() {
        try {
            new Thread(() -> {int a = 1/0;}).start();
        } catch (Exception e) {
            System.out.println("捕获到线程抛出的异常！");
            e.printStackTrace();
        }
    }
}
