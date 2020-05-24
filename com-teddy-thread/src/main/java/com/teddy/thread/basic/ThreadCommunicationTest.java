package com.teddy.thread.basic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ThreadCommunicationTest {

    private static class MySignal {
        private boolean hasDataToProcess = false;
        public synchronized boolean hasDataToProcess() {
            return this.hasDataToProcess;
        }
        public synchronized void setHasDataToProcess(boolean process){
            this.hasDataToProcess = process;
        }
    }

    private void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_share_signal() {
        MySignal signal = new MySignal();
        List<String> data = new ArrayList<>();
        new Thread(() -> {
            while(!signal.hasDataToProcess()) {
                System.out.println("线程A未接收到信号，sleep 1000ms");
                sleep(1000);
            }
            System.out.println("线程A接收到信号了，开始处理：" + data.remove(0));
        }).start();

        sleep(10000);

        new Thread(() -> {
            System.out.println("线程B设置信号");
            data.add("线程B设置的数据");
            signal.setHasDataToProcess(true);
        }).start();
    }

    @Test
    public void test_wait_notify() {
        Object monitor = new Object();
        List<String> data = new ArrayList<>();
        Thread thread = new Thread(() -> {
            try {
                synchronized (monitor) {
                    System.out.println("子线程开始等待notify信号");
                    monitor.wait();
                }
                System.out.println("子线程接收到notify信号了，开始处理：" + data.remove(0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        sleep(3000);
        data.add("主线程调用notify方法");
        synchronized (monitor) {
            monitor.notify();
        }
    }
}
