package com.teddy.leetcode.a1两数之和;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoNumberSumTest {



    @Test
    void twoSum() {
        TwoNumberSum sum = new TwoNumberSum();
        assertArrayEquals(new int[]{3,4}, sum.twoSum(new int[]{1,2,3,4,5},9));
        assertArrayEquals(new int[]{1,2}, sum.twoSum(new int[]{3,2,4},6));
        assertArrayEquals(new int[]{0,1}, sum.twoSum(new int[]{3,3},6));
        assertArrayEquals(new int[]{1,2}, sum.twoSum(new int[]{2,5,5,11},10));
        assertArrayEquals(new int[]{0,3}, sum.twoSum(new int[]{0,4,3,0},0));
        assertArrayEquals(new int[]{0,2}, sum.twoSum(new int[]{-3,4,3,90},0));
    }
}