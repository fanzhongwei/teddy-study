package com.teddy.thread.basic;

import java.util.Random;

import org.junit.Test;

/**
 * package com.teddy.thread
 * description: 线程方法
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-15 23:15
 */
public class ThreadMethodTest {

    @Test
    public void currentThreadTest(){
        System.out.println(Thread.currentThread().getName());
    }

    static class ThreadNameTest extends Thread{
        public ThreadNameTest(){
            System.out.println("执行构造方法的线程：" + Thread.currentThread().getName());
        }

        @Override
        public void run(){
            System.out.println("执行run方法的线程：" + Thread.currentThread().getName());
        }
    }

    @Test
    public void threadNameTest() throws InterruptedException {
        ThreadNameTest threadNameTest = new ThreadNameTest();
        threadNameTest.setName("threadNameTest");
        threadNameTest.start();

        Thread.sleep(100);
    }

    @Test
    /**
     * isAlive是测试线程是否处于活动状态
     */
    public void threadIsAliveTest() throws InterruptedException {
        Thread thread = new Thread(()->System.out.println("run=" + Thread.currentThread().isAlive()));
        System.out.println("begin == " + thread.isAlive());
        thread.start();
        System.out.println("end == " + thread.isAlive());
    }

    @Test
    public void threadSleepTest() throws InterruptedException {
        Thread thread = new Thread(()->{
            System.out.println("run thread=" + Thread.currentThread().getName() + " begin=" + System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run thread=" + Thread.currentThread().getName() + " end=" + System.currentTimeMillis());
        });

        System.out.println("begin=" + System.currentTimeMillis());
        thread.start();

        for (int i = 0; i < 30; i++) {
            System.out.println(i);
            Thread.sleep(100);
        }
        System.out.println("end=" + System.currentTimeMillis());
    }

    @Test
    public void threadIdTest(){
        System.out.println(Thread.currentThread().getName() + " id=" + Thread.currentThread().getId());
    }

    @Test
    public void threadStopTest() throws InterruptedException {
        Thread thread = foreachThread(10);
        thread.start();

        System.out.println(thread.getName() + " isAlive=" + thread.isAlive());
        Thread.sleep(2);
        thread.stop();
        Thread.sleep(100);
        System.out.println(thread.getName() + " isAlive=" + thread.isAlive());
    }

    @Test
    public void threadStopThrowThreadDeathExceptionTest() throws InterruptedException {
        Thread thread = new Thread(()->{
            try {
                Thread.currentThread().stop();
            }catch (ThreadDeath e){
                System.out.println("进入catch()方法!");
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(10);
    }

    @Test
    public void threadInterruptTest() throws InterruptedException {
        Thread thread = foreachThread(10);
        thread.start();
        thread.interrupt();
        Thread.sleep(100);
    }

    private Thread foreachThread(int times) {
        return new Thread(()->{
            for (int i = 0; i < times; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void threadInterruptedTest(){
        Thread.currentThread().interrupt();
        System.out.println("是否停止1？=" + Thread.interrupted());
        System.out.println("是否停止2？=" + Thread.interrupted());
    }

    @Test
    public void threadIsInterruptedTest() throws InterruptedException {
        Thread thread = foreachThread(10);
        thread.start();
        thread.interrupt();
        System.out.println("是否停止1？=" + thread.isInterrupted());
        System.out.println("是否停止2？=" + thread.isInterrupted());
        Thread.sleep(100);
    }

    @Test
    public void threadUseInterruptToStop() throws InterruptedException {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 1000000000; i++) {
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("线程停止了...");
                    return;
                }
                System.out.println(i);
            }
        });
        thread.start();
        Thread.sleep(100);
        System.out.println("is running =" + thread.isAlive());
        thread.interrupt();
        Thread.sleep(100);
        System.out.println("is running =" + thread.isAlive());
    }

    @Test
    public void threadInterruptAtSleep() throws InterruptedException {
        Thread thread = new Thread(()-> {
            System.out.println("run start...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("糟糕，出异常...");
                e.printStackTrace();
            }
            System.out.println("run end...");
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("is running =" + thread.isAlive());
        thread.interrupt();
        Thread.sleep(100);
        System.out.println("is running =" + thread.isAlive());
        Thread.sleep(2000);
    }

    @Test
    public void threadSuspendAndResumeTest() throws InterruptedException {
        Thread thread = new Thread(()->{
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        Thread.sleep(2000);
        thread.suspend();
        System.out.println("暂停3000ms... 当前时间=" + System.currentTimeMillis());
        Thread.sleep(3000);

        System.out.println("继续运行...当前时间=" + System.currentTimeMillis());
        thread.resume();

        Thread.sleep(10000);
    }

    static class SynchronizedObject{
        synchronized public void print(){
            System.out.println("begin...");
            if(Thread.currentThread().getName().equals("a")){
                System.out.println("a线程永远suspend了！");
                Thread.currentThread().suspend();
            }
            System.out.println("end...");
        }
    }

    @Test
    public void threadSuspendDeadLock() throws InterruptedException {
        final SynchronizedObject object = new SynchronizedObject();
        Thread threadA = new Thread(()->object.print());
        threadA.setName("a");
        threadA.start();

        Thread.sleep(1000);

        Thread threadB = new Thread(()-> {
            System.out.println("threadB启动了，但是进不了print方法，只打印了一个begin...");
            System.out.println("因为print() 方法被线程a锁定了并且永远suspend，暂停了...");
            object.print();
        });
        threadB.start();

        Thread.sleep(10000);
    }

    @Test
    public void threadYieldTest() throws InterruptedException {
        Thread thread = new Thread(()->{
            long beginTime = System.currentTimeMillis();
            int count = 0;
            for (int i = 0; i < 50000000; i++) {
                count++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("用时：" + (endTime - beginTime) + "毫秒！");
        });

        Thread threadYield = new Thread(()->{
            long beginTime = System.currentTimeMillis();
            int count = 0;
            for (int i = 0; i < 50000000; i++) {
                Thread.yield();
                count++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("调用Yield方法用时：" + (endTime - beginTime) + "毫秒！");
        });

        thread.start();
        threadYield.start();
        thread.join();
        threadYield.join();
//        while (thread.isAlive() || threadYield.isAlive()){
//            Thread.sleep(1000);
//        }
    }

    @Test
    public void threadPriorityTest() throws InterruptedException {
        Thread threadA = new Thread(()->{
            long beginTime = System.currentTimeMillis();
            long add = 0;
            for (int i = 0; i < 500000; i++) {
                Random random = new Random();
                random.nextInt();
                add++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("threadA用时：" + (endTime - beginTime) + "毫秒！");
        });


        Thread threadB = new Thread(()->{
            long beginTime = System.currentTimeMillis();
            long add = 0;
            for (int i = 0; i < 500000; i++) {
                Random random = new Random();
                random.nextInt();
                add++;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("threadB用时：" + (endTime - beginTime) + "毫秒！");
        });

        for (int i = 0; i < 5; i++) {
            Thread thread1 = new Thread(threadA);
            Thread thread2 = new Thread(threadB);
            thread1.setPriority(8);
            thread1.start();
            thread2.setPriority(1);
            thread2.start();
        }
        Thread.sleep(2000);
    }
}
