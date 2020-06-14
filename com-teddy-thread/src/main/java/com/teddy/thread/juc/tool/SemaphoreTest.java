package com.teddy.thread.juc.tool;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {
    public static void main(String[] args) {
        //同时只能进5个人
        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 15; i++) {
            new Thread(()->{
                try {
                    //获得许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"进店购物");
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(Thread.currentThread().getName()+"出店");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //释放许可
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
