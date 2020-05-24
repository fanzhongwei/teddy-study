package com.teddy.thread.juc.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * package com.teddy.thread
 * description: Lock
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 10:53
 */
public class ThreadLockTest {

    private Lock lock = new ReentrantLock();

    /**
     * 实例变量非线程安全的
     */
    private int instanceNum;

    private void addInstanceNum(String username) {
        try {
            lock.lock();
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
            lock.unlock();
        }
    }

    @Test
    public void threadSetInstanceNumTest() throws InterruptedException {
        Thread threadA = new Thread(() -> addInstanceNum("a"));
        Thread threadB = new Thread(() -> addInstanceNum("b"));
        threadA.start();
        threadB.start();
        Thread.sleep(3000);
    }

    static class ReentrantLockTest{
        Lock lock = new ReentrantLock();
        void testMethod(){
            lock.lock();
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " " + (i + 1));
            }
            testMethodB();
            lock.unlock();
        }

        void testMethodB(){
            lock.lock();
            System.out.println("ThreadName = " + Thread.currentThread().getName() + " 获取到了lock，进入testMethodB.");
            lock.unlock();
        }
    }

    @Test
    /**
     * 调用lock.lock()代码的线程就持有了“对象监视器”，其他线程只有等待锁被释放时再次争抢，效果和synchronized关键字一样。
     * 顺序是随机
     */
    public void reentrantLockTest() throws InterruptedException {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        Thread thread1 = new Thread(()-> reentrantLockTest.testMethod());
        Thread thread2 = new Thread(()-> reentrantLockTest.testMethod());
        Thread thread3 = new Thread(()-> reentrantLockTest.testMethod());
        Thread thread4 = new Thread(()-> reentrantLockTest.testMethod());
        Thread thread5 = new Thread(()-> reentrantLockTest.testMethod());
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        Thread.sleep(1000);
    }

    private synchronized void throwExceptionReleaseLock(){
        for (int i = 0; i < 10; i++) {
            if(i == 5 && "a".equals(Thread.currentThread().getName())){
                throw new RuntimeException("threadName = " + Thread.currentThread().getName() + "抛个异常，自动释放Lock...");
            }
            System.out.println("threadName = " + Thread.currentThread().getName() + " i = " + i);
        }
    }

    @Test
    /**
     * synchronized 出现异常会自动释放锁
     */
    public void throwExceptionReleaseLockTest() throws InterruptedException {
        Thread threadA = new Thread(()->throwExceptionReleaseLock());
        Thread threadB = new Thread(()->throwExceptionReleaseLock());

        threadA.setName("a");
        threadA.start();
        Thread.sleep(1000);

        threadB.setName("b");
        threadB.start();

        Thread.sleep(1000);
    }

    private void throwExceptionNotReleaseLock(Lock lock){
        lock.lock();
        for (int i = 0; i < 10; i++) {
            if(i == 5 && "a".equals(Thread.currentThread().getName())){
                throw new RuntimeException("threadName = " + Thread.currentThread().getName() + "抛个异常，死锁啦...");
            }
            System.out.println("threadName = " + Thread.currentThread().getName() + " i = " + i);
        }
        lock.unlock();
    }

    @Test
    /**
     * Lock 出现异常会需要手动释放锁
     */
    public void throwExceptionNotReleaseLockTest() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Thread threadA = new Thread(()->throwExceptionNotReleaseLock(lock));
        Thread threadB = new Thread(()->throwExceptionNotReleaseLock(lock));

        threadA.setName("a");
        threadA.start();
        Thread.sleep(1000);

        threadB.setName("b");
        threadB.start();

        Thread.sleep(1000);
    }

    private void lockIsFair(Lock lock){
        try {
            System.out.println("ThreadName = " + Thread.currentThread().getName() + " 请求了锁...");
            lock.lock();
            System.out.println("ThreadName = " + Thread.currentThread().getName() + " 获得了锁...");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @Test
    /**
     * 锁分为“公平锁”和“非公平锁”，ReentrantLock默认是非公平锁
     * 公平锁：线程获取锁额顺序是按照线程枷锁的顺序来分配的，FIFO。
     * 非公平锁：是一种获取锁的抢占机制，是随机获得锁的，先来的不一定先得到锁，可能导致某些线程一直拿不到锁。
     */
    public void lockIsFairTest() throws InterruptedException {
        Lock notFairLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(()-> lockIsFair(notFairLock));
            thread.setName("not fair lock thread" + i);
            thread.start();
        }
        Thread.sleep(600);

        Lock fairLock = new ReentrantLock(true);
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(()-> lockIsFair(fairLock));
            thread.setName("fair lock thread" + i);
            thread.start();
        }
        Thread.sleep(600);
    }

    /**
     * 读写锁将对一个资源（比如文件）的访问分成了2个锁，一个读锁和一个写锁。
     *
     *
     * ReadWriteLock就是读写锁，它是一个接口，ReentrantReadWriteLock实现了这个接口。
     *  可以通过readLock()获取读锁，只要没有线程拥有写锁（writers==0），
     * 且没有线程在请求写锁（writeRequests ==0），所有想获得读锁的线程都能成功获取。
     *
     * 通过writeLock()获取写锁，当一个线程想获得写锁的时候，首先会把写锁请求数加1（writeRequests++），
     * 然后再去判断是否能够真能获得写锁，当没有线程持有读锁（readers==0 ）,
     * 且没有线程持有写锁（writers==0）时就能获得写锁。有多少线程在请求写锁并无关系。
     */
    static class ReadWriteReentrantLockTest{
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        void read(){
            try {
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " 请求读锁 time = " + System.currentTimeMillis());
                lock.readLock().lock();
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " 获取读锁 time = " + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " 读取完成 time = " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        }

        void write(){
            try {
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " 请求写锁 time = " + System.currentTimeMillis());
                lock.writeLock().lock();
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " 获取写锁 time = " + System.currentTimeMillis());
                Thread.sleep(5000);
                System.out.println("ThreadName = " + Thread.currentThread().getName() + " 写入完成 time = " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    @Test
    /**
     * 读读共享
     */
    public void asyncReadTest() throws InterruptedException {
        ReadWriteReentrantLockTest readWriteReentrantLockTest = new ReadWriteReentrantLockTest();
        Thread threadA = new Thread(()-> readWriteReentrantLockTest.read());
        threadA.setName("A");
        threadA.start();

        Thread threadB = new Thread(()-> readWriteReentrantLockTest.read());
        threadB.setName("B");
        threadB.start();

        Thread.sleep(6000);
    }

    @Test
    /**
     * 写写互斥
     */
    public void syncWriteTest() throws InterruptedException {
        ReadWriteReentrantLockTest readWriteReentrantLockTest = new ReadWriteReentrantLockTest();
        Thread threadA = new Thread(()-> readWriteReentrantLockTest.write());
        threadA.setName("A");
        threadA.start();

        Thread threadB = new Thread(()-> readWriteReentrantLockTest.write());
        threadB.setName("B");
        threadB.start();

        Thread.sleep(11000);
    }

    @Test
    /**
     * 读写互斥
     */
    public void syncReadWriteTest() throws InterruptedException {
        ReadWriteReentrantLockTest readWriteReentrantLockTest = new ReadWriteReentrantLockTest();
        Thread threadA = new Thread(()-> readWriteReentrantLockTest.read());
        threadA.setName("A");
        threadA.start();

        Thread.sleep(1000);

        Thread threadB = new Thread(()-> readWriteReentrantLockTest.write());
        threadB.setName("B");
        threadB.start();

        Thread.sleep(11000);
    }

    @Test
    /**
     * 写读互斥
     */
    public void syncWriteReadTest() throws InterruptedException {
        ReadWriteReentrantLockTest readWriteReentrantLockTest = new ReadWriteReentrantLockTest();
        Thread threadA = new Thread(()-> readWriteReentrantLockTest.write());
        threadA.setName("A");
        threadA.start();

        Thread.sleep(1000);

        Thread threadB = new Thread(()-> readWriteReentrantLockTest.read());
        threadB.setName("B");
        threadB.start();

        Thread.sleep(11000);
    }
}
