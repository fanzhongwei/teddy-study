package com.teddy.thread.basic;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    @Test
    public void test() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("进入线程一，LockSupport.park()，" + System.currentTimeMillis());
            LockSupport.park();
            System.out.println("退出线程一，接收到LockSupport.unpark(thread)，" + System.currentTimeMillis());
        });
        thread.start();

        Thread.sleep(2000);

        new Thread(() -> {
            System.out.println("进入线程二，LockSupport.unpark()，" + System.currentTimeMillis());
            LockSupport.unpark(thread);
        }).start();
    }
}
