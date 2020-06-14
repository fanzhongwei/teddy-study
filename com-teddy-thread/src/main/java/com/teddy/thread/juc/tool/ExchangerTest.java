package com.teddy.thread.juc.tool;

import java.util.concurrent.Exchanger;

public class ExchangerTest {
    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            String t = "t1";
            try {
                t = exchanger.exchange(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + t);
        });
        Thread t2 = new Thread(() -> {
            String t = "t2";
            try {
                t = exchanger.exchange(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-" + t);
        });
        t1.start();
        t2.start();
    }
}
