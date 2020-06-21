package com.teddy.thread.model.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinWork extends RecursiveTask<Long> {

    private Long start;
    private Long end;
    //临界值
    public static final int LEAF_TARGET = ForkJoinPool.getCommonPoolParallelism() << 2;
    public static long CRITICAL;

    public ForkJoinWork(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public static long suggestTargetSize(long sizeEstimate) {
        if (CRITICAL != 0) {
            return CRITICAL;
        }
        long est = sizeEstimate / LEAF_TARGET;
        if (est > 0L) {
            CRITICAL = est;
        } else {
            CRITICAL = 1L;
        }
        return CRITICAL;
    }

    @Override
    protected Long compute() {
        //判断是否是拆分完毕
        Long length = end - start;
        if (length <= suggestTargetSize(length)) {
            //如果拆分完毕就相加
            return LongStream.rangeClosed(start, end).reduce(0, Long::sum);
        } else {
            //没有拆分完毕就开始拆分
            Long middle = (end + start) / 2;
            ForkJoinWork right = new ForkJoinWork(start, middle);
            //拆分，并压入线程队列
            right.fork();

            ForkJoinWork left = new ForkJoinWork(middle + 1, end);
            //拆分，并压入线程队列
            left.fork();

            //合并
            return right.join() + left.join();
        }
    }
}
