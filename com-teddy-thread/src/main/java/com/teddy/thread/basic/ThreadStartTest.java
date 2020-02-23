package com.teddy.thread.basic;

import org.junit.Test;

/**
 * package com.teddy.thread
 * description: 如何Thread创建
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-15 23:01
 */
public class ThreadStartTest {

    /**
     * 继承Thread类
     */
    static class ThreadTest extends Thread{
        @Override
        public void run(){
            super.run();
            System.out.println("Hello World! This is my first Thread.");
        }
    }

    /**
     * 实现Runnable接口
     */
    static class RunnableTest implements Runnable{
        @Override
        public void run() {
            System.out.println("Hello World! This is my first Thread.");
        }
    }

    @Test
    public void extendsThreadTest() throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();
        threadTest.start();
        System.out.println("运行结束。");
        Thread.sleep(10);
    }

    @Test
    public void implementsRunnableTest() throws InterruptedException {
        RunnableTest runnableTest = new RunnableTest();
        Thread thread = new Thread(runnableTest);
        thread.start();

        Thread.sleep(1000);
        System.out.println("运行结束。");
    }
}
