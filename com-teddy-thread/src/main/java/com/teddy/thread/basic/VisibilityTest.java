package com.teddy.thread.basic;

import org.junit.Test;

/**
 * package com.teddy.thread
 * description: 可见性
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 12:45
 */
public class VisibilityTest {

    boolean isRunning = true;
    volatile boolean visibleIsRunning = true;
    void methodA(){
        while (isRunning){}
        System.out.println("线程停止了！");
    }

    void methodB(){
        while (visibleIsRunning){}
        System.out.println("线程停止了！");
    }

    @Test
    public void invisibleTest() throws InterruptedException {
        VisibilityTest test = new VisibilityTest();
        Thread thread = new Thread(()->test.methodA());
        thread.start();
        Thread.sleep(1000);

        test.isRunning = false;
        System.out.println("isRunning 设置为false");
        Thread.sleep(10000);
    }

    @Test
    /**
     * 关键字volatile提示线程每次从主存中读取变量，而不是从工作内存中读取，这样子就保证了共享变量在多线程中的可见性。
     */
    public void visibleTest() throws InterruptedException {
        VisibilityTest test = new VisibilityTest();
        Thread thread = new Thread(()->test.methodB());
        thread.start();
        Thread.sleep(1000);

        test.visibleIsRunning = false;
        System.out.println("isRunning 设置为false");
        Thread.sleep(10000);
    }
}
