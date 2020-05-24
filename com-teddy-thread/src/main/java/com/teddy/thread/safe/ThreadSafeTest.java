package com.teddy.thread.safe;

import org.junit.Test;

/**
 * package com.teddy.thread
 * description: 线程安全
 * Copyright 2018 Teddy, Inc. All rights reserved.
 *
 * @author Teddy
 * @date 2018-9-16 9:15
 */
public class ThreadSafeTest {

    /**
     * 方法内的变量是线程安全的
     * @param username
     */
    private void addI(String username){
        try {
            int num;
            if("a".equals(username)){
                num = 100;
                System.out.println("a set over!");
                Thread.sleep(2000);
            }else{
                num = 200;
                System.out.println("b set over!");
            }
            System.out.println(username + " num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void threadSetPrivateNumTest() throws InterruptedException {
        Thread threadA = new Thread(()->addI("a"));
        Thread threadB = new Thread(()->addI("b"));
        threadA.start();
        threadB.start();
        Thread.sleep(3000);
    }

    /** 实例变量非线程安全的 */
    private int instanceNum;
    private void addInstanceNum(String username){
        try {
            if("b".equals(username)){
                instanceNum = 200;
                System.out.println("b set over!");
            }else{
                instanceNum = 100;
                System.out.println("a set over!");
                Thread.sleep(2000);
            }
            System.out.println(username + " num = " + instanceNum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void threadSetInstanceNumTest() throws InterruptedException {
        Thread threadA = new Thread(()->addInstanceNum("a"));
        Thread threadB = new Thread(()->addInstanceNum("b"));
        threadA.start();
        threadB.start();
        Thread.sleep(3000);
    }

    @Test
    public void localObjectTest() throws InterruptedException {
        ThreadSafeTest test = new ThreadSafeTest();
        Thread threadA = new Thread(()->addInstanceNum("a"));
        Thread threadB = new Thread(()->addInstanceNum("b"));
        threadA.start();
        threadB.start();
        Thread.sleep(3000);
    }

    /**
     * 使用synchronized保证事例变量的线程安全
     * @param username
     */
    private synchronized void syncAddInstanceNum(String username){
        addInstanceNum(username);
    }

    @Test
    public void threadSyncSetInstanceNumTest() throws InterruptedException {
        Thread threadA = new Thread(()->syncAddInstanceNum("a"));
        Thread threadB = new Thread(()->syncAddInstanceNum("b"));
        threadA.start();
        threadB.start();
        Thread.sleep(3000);
    }

    static class SyncMethodLockTest{
        void methodA(){
            try {
                System.out.println("begin methodA threadName = " + Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println("end time = " + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        void methodB(){
            synchronized (this){
                methodA();
            }
        }
    }

    @Test
    public void threadsSyncEnterMethodTest() throws InterruptedException {
        SyncMethodLockTest methodLockTest = new SyncMethodLockTest();
        Thread threadA = new Thread(()->methodLockTest.methodA());
        Thread threadB = new Thread(()->methodLockTest.methodA());

        threadA.start();
        threadB.start();
        Thread.sleep(5000);
    }

    @Test
    /**
     * 调用synchronized声明的方法一定是排队运行的，只有“共享资源”的读写访问才需要同步。
     */
    public void threadsAsyncEnterMethodTest() throws InterruptedException {
        SyncMethodLockTest methodLockTest = new SyncMethodLockTest();
        Thread threadA = new Thread(()->methodLockTest.methodB());
        Thread threadB = new Thread(()->methodLockTest.methodB());

        threadA.start();
        threadB.start();
        Thread.sleep(5000);
    }

    @Test
    /**
     * threadA先持有methodLockTest的Lock锁，threadB还是可以以异步的方式调用methodLockTest对象中的非synchronized类型的方法。
     * 如果threadA先持有methodLockTest的Lock锁，threadB调用methodLockTest对象中的synchronized类型的方法这是同步的，需要等待。
     */
    public void lockObjectTest() throws InterruptedException {
        SyncMethodLockTest methodLockTest = new SyncMethodLockTest();
        Thread threadA = new Thread(()->methodLockTest.methodB());
        Thread threadB = new Thread(()->methodLockTest.methodA());

        threadA.start();
        threadB.start();
        Thread.sleep(5000);
    }

    static class SyncStaticMethodTest{
        synchronized static void methodA(){
            System.out.println("threadName = " + Thread.currentThread().getName() + " enter sync static methodA.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadName = " + Thread.currentThread().getName() + " leave sync static methodA.");
        }

        synchronized void methodB(){
            System.out.println("threadName = " + Thread.currentThread().getName() + " enter sync methodB.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("threadName = " + Thread.currentThread().getName() + " leave sync methodB.");
        }
    }
    @Test
    public void syncStaticMethodTest() throws InterruptedException {
        SyncStaticMethodTest syncStaticMethodTest = new SyncStaticMethodTest();
        Thread threadA = new Thread(()->syncStaticMethodTest.methodA());
        threadA.setName("a");
        threadA.start();

        Thread threadB = new Thread(()->syncStaticMethodTest.methodB());
        threadB.setName("b");
        threadB.start();

        Thread.sleep(5000);
    }

    static class SyncCodeBlockTest{

        void methodA() {
            System.out.println("methodA time = " + System.currentTimeMillis());
            synchronized (this){
                System.out.println("methodA begin time = " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("methodA end time = " + System.currentTimeMillis());
            }
        }
        void methodB(){
            System.out.println("methodB time = " + System.currentTimeMillis());
            synchronized (this){
                System.out.println("methodB begin time = " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("methodB end time = " + System.currentTimeMillis());
            }
        }
    }
    @Test
    /**
     * 被synchronized修饰的代码块儿也是同步执行的
     */
    public void syncCodeBlockTest() throws InterruptedException {
        SyncCodeBlockTest syncCodeBlockTest = new SyncCodeBlockTest();
        Thread threadA = new Thread(()-> syncCodeBlockTest.methodA());
        threadA.start();
        Thread threadB = new Thread(()-> syncCodeBlockTest.methodB());
        threadB.start();

        Thread.sleep(5000);
    }

    private static class InStaticMethodSync{
        public static void methodA(){
            synchronized(InStaticMethodSync.class){
                System.out.println("methodA begin time = " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("methodA begin time = " + System.currentTimeMillis());
        }

        public static void methodB(){
            synchronized(InStaticMethodSync.class){
                System.out.println("methodB begin time = " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("methodB begin time = " + System.currentTimeMillis());
        }
    }

    @Test
    public void inStaticMethodSyncTest() throws InterruptedException {
        InStaticMethodSync inStaticMethodSync = new InStaticMethodSync();
        Thread threadA = new Thread(()-> inStaticMethodSync.methodA());
        threadA.start();
        Thread threadB = new Thread(()-> inStaticMethodSync.methodB());
        threadB.start();

        Thread.sleep(5000);
    }

    static class SyncCodeBlockWithNotThisTest{
        private String str = new String();
        void methodA(){
            synchronized (str){
                System.out.println("threadName = " + Thread.currentThread().getName() + " enter methodA. time = " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadName = " + Thread.currentThread().getName() + " leave methodA. time = " + System.currentTimeMillis());
            }
        }
        private String str1 = "lock";
        void methodB(){
            synchronized (str1){
                System.out.println("threadName = " + Thread.currentThread().getName() + " enter methodB. time = " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadName = " + Thread.currentThread().getName() + " leave methodB. time = " + System.currentTimeMillis());
            }
        }
    }

    @Test
    public void syncCodeBlockWithNotThisTest() throws InterruptedException {
        SyncCodeBlockWithNotThisTest syncTest1 = new SyncCodeBlockWithNotThisTest();
        Thread threadA = new Thread(syncTest1::methodA);
        threadA.setName("A");
        threadA.start();

        SyncCodeBlockWithNotThisTest syncTest2 = new SyncCodeBlockWithNotThisTest();
        Thread threadB = new Thread(()-> syncTest2.methodA());
        threadB.setName("B");
        threadB.start();

        Thread.sleep(5000);
    }
    @Test
    /**
     * 尽量不要用String作为锁对象
     */
    public void asyncCodeBlockWithNotThisTest() throws InterruptedException {
        SyncCodeBlockWithNotThisTest syncTest1 = new SyncCodeBlockWithNotThisTest();
        Thread threadA = new Thread(()-> syncTest1.methodB());
        threadA.setName("A");
        threadA.start();

        SyncCodeBlockWithNotThisTest syncTest2 = new SyncCodeBlockWithNotThisTest();
        Thread threadB = new Thread(()-> syncTest2.methodB());
        threadB.setName("B");
        threadB.start();

        Thread.sleep(5000);
    }

    static class ReentrantSyncMethodTest{
        synchronized void methodA(){
            System.out.println("methodA");
            methodB();
        }
        synchronized void methodB(){
            System.out.println("methodB");
        }
    }
    @Test
    /**
     * 可重入锁：自己可以再次获取自己的内部锁
     *
     * 如果不可重入的话
     * 线程进入methodA获取了该对象的锁，然后执行methodB还是需要获取该对象的锁，
     * 但是methodA还没有执行完不会将锁释放，就会造成死锁。
     */
    public void reentrantSyncMethodTest(){
        ReentrantSyncMethodTest reentrantSyncMethodTest = new ReentrantSyncMethodTest();
        Thread thread = new Thread(()-> reentrantSyncMethodTest.methodA());
        thread.start();
    }
}
