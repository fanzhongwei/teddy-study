package com.teddy.thread.juc.tool;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) {
        //优先执行，执行完毕之后，才能执行 main
        //1、实例化计数器，10
        CountDownLatch countDownLatch = new CountDownLatch(10);
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("++++++++++Thread");
                countDownLatch.countDown();
            }
        }).start();
        //2、调用 await 方法 让主线程等待countdonwn运行完毕
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main--------------");
    }
}
