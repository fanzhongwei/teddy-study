package com.teddy.thread.lock;

import org.junit.Test;

/**
 * package com.teddy.thread.lock
 * description: 简单锁实现
 * Copyright 2019 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2019-4-15 0:20:55
 */
public class CustomLock {

    static class SimpleLock {
        private boolean isLocked = false;

        public synchronized void lock()
                throws InterruptedException {
            while (isLocked) {
                wait();
            }
            isLocked = true;
        }

        public synchronized void unlock() {
            isLocked = false;
            notify();
        }
    }

    private SimpleLock simpleLock = new SimpleLock();
    private int instanceNum;

    private void simpleLockTest(String username) {
        try {
            simpleLock.lock();
            if ("b".equals(username)) {
                instanceNum = 200;
                System.out.println("b set over!");
            } else {
                instanceNum = 100;
                System.out.println("a set over!");
                Thread.sleep(2000);
            }
            System.out.println(username + " num = " + instanceNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            simpleLock.unlock();
        }
    }

    @Test
    public void threadSetInstanceNumTest() throws InterruptedException {
        Thread threadA = new Thread(() -> simpleLockTest("a"));
        Thread threadB = new Thread(() -> simpleLockTest("b"));
        threadA.start();
        threadB.start();
        Thread.sleep(3000);
    }

    private void notReentrantLockTestA(String name) throws InterruptedException {
        System.out.println(name + " 开始请求锁");
        simpleLock.lock();
        System.out.println(name + " 获取到了锁");
        notReentrantLockTestB("内部调用，判断是否可重入，不可重入的话就会造成死锁...");
        simpleLock.unlock();
        System.out.println(name + " 释放了锁");
    }

    private void notReentrantLockTestB(String name) throws InterruptedException {
        System.out.println(name + " 开始请求锁");
        simpleLock.lock();
        System.out.println(name + " 获取到了锁");

        simpleLock.unlock();
        System.out.println(name + " 释放了锁");
    }

    @Test
    public void notReentrantLockTest() throws InterruptedException {
        notReentrantLockTestA("外部调用");
    }


    public class SimpleReentrantLock{
        boolean isLocked = false;
        Thread  lockedBy = null;
        int lockedCount = 0;

        public synchronized void lock()
                throws InterruptedException{
            Thread callingThread =
                    Thread.currentThread();
            while(isLocked && lockedBy != callingThread){
                wait();
            }
            isLocked = true;
            lockedCount++;
            lockedBy = callingThread;
        }

        public synchronized void unlock(){
            if(Thread.currentThread() ==
                    this.lockedBy){
                lockedCount--;
                if(lockedCount == 0){
                    isLocked = false;
                    notify();
                }
            }
        }
    }

    private SimpleReentrantLock simpleReentrantLock = new SimpleReentrantLock();
    private void reentrantLockTestA(String name) throws InterruptedException {
        System.out.println(name + " 开始请求锁");
        simpleReentrantLock.lock();
        System.out.println(name + " 获取到了锁");
        reentrantLockTestB("内部调用，判断是否可重入，不可重入的话就会造成死锁...");
        simpleReentrantLock.unlock();
        System.out.println(name + " 释放了锁");
    }

    private void reentrantLockTestB(String name) throws InterruptedException {
        System.out.println(name + " 开始请求锁");
        simpleReentrantLock.lock();
        System.out.println(name + " 获取到了锁");

        simpleReentrantLock.unlock();
        System.out.println(name + " 释放了锁");
    }

    @Test
    public void reentrantLockTest() throws InterruptedException {
        reentrantLockTestA("外部调用");
    }
}
