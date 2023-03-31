package com.teddy.leetcode.a2两数相加;

import java.math.BigDecimal;

/**
 * <a href="https://leetcode.cn/problems/add-two-numbers/">两数相加</a>
 *
 * @author fanzhongwei
 * @date 2023/03/07 20:49
 **/
public class TwoNumberAdd {

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigDecimal add = getNumber(l1).add(getNumber(l2));
        String result = add.toString();
        int val = Character.getNumericValue(result.charAt(0));
        ListNode head = new ListNode(val);
        for (int i = 1; i <= result.length() - 1; i++) {
            val = Character.getNumericValue(result.charAt(i));
            head = new ListNode(val, head);
        }
        return head;
    }

    private BigDecimal getNumber(ListNode node) {
        // [2,4,3] => 342
        StringBuilder n = new StringBuilder(50);
        n.append(node.val);
        while (node.next != null) {
            node = node.next;
            n.append(node.val);
        }
        n.reverse();
        return new BigDecimal(n.toString());
    }

    public static final class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
