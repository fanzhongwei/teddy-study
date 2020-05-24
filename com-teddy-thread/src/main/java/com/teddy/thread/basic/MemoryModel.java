package com.teddy.thread.basic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryModel {

    private static class MemorySharedObject {
        private static MemorySharedObject sharedObject = new MemorySharedObject();
        public Integer object2 = new Integer(222);
        public Integer object4 = new Integer(444);

        public long member1 = 12345L;
        public long member2 = 67890L;
    }

    private static List<Object> list = Collections.synchronizedList(new ArrayList<>());

    @Test
    public void test() throws InterruptedException {
        new Thread(this::methodOne).start();
        new Thread(this::methodOne).start();

        Thread.sleep(100000);
        // jmap -dump:format=b,file=./heap_dump.txt 12792
        // dump出jvm内存后，使用mat进行分析，可以找到本例中对象的情况
    }

    private void methodOne() {
        int localVariable1 = 999;
        MemorySharedObject localVariable2 = MemorySharedObject.sharedObject;

        list.add(localVariable2);
        methodTwo();
    }

    private void methodTwo() {
        Integer localVariable1 = new Integer(4321);
        list.add(localVariable1);
    }
}
