package com.teddy.thread.model.forkjoin;

import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ForkJoinPoolTest {

    @Test
    public void fork_join_test() {
        //ForkJoin实现
        long startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(16);
        ForkJoinTask<Long> task = new ForkJoinWork(0L, 20000000000L);
        Long invoke = forkJoinPool.invoke(task);
        long endTime = System.currentTimeMillis();
        System.out.println("invoke = " + invoke + "  time: " + (endTime - startTime));
        //invoke = -2914184800805067776  time: 2873
    }

    @Test
    public void single_thread_test() {
        //普通单线程实现
        long startTime = System.currentTimeMillis();
        long x = LongStream.rangeClosed(0, 20000000000L).reduce(0, Long::sum);
        long entTime = System.currentTimeMillis();
        System.out.println("invoke = " + x + "  time: " + (entTime - startTime));
        // invoke = -2914184800805067776  time: 8046
    }

    @Test
    public void parallel_test() {
        //Java 8 并行流的实现
        long startTime = System.currentTimeMillis();
        long reduce = LongStream.rangeClosed(0, 20000000000L).parallel().reduce(0, Long::sum);
        long endTime = System.currentTimeMillis();
        System.out.println("invoke = " + reduce + "  time: " + (endTime - startTime));
        //invoke = -2914184800805067776  time: 2188
    }

}
