package com.teddy.leetcode.a2两数相加;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoNumberAddTest {

    @Test
    void addTwoNumbers() {
        // 输入：l1 = [2,4,3], l2 = [5,6,4]
        // 输出：[7,0,8]
        TwoNumberAdd.ListNode head1 = new TwoNumberAdd.ListNode(2, new TwoNumberAdd.ListNode(4, new TwoNumberAdd.ListNode(3)));
        TwoNumberAdd.ListNode head2 = new TwoNumberAdd.ListNode(5, new TwoNumberAdd.ListNode(6, new TwoNumberAdd.ListNode(4)));

        TwoNumberAdd add = new TwoNumberAdd();
        add.addTwoNumbers(head1,head2);
    }
}