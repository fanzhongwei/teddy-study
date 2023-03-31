package com.teddy.leetcode.a4两数组中位数;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ToSortedArrayMedianTest {

    @Test
    void findMedianSortedArrays() {
        ToSortedArrayMedian median = new ToSortedArrayMedian();
        assertEquals(3.0, median.findMedianSortedArrays(new int[]{1,2,3,4,5}, new int[]{2,3,4}));
        assertEquals(3.0, median.findMedianSortedArrays(new int[]{1,2,3,4,5,6}, new int[]{2,3,4}));
        assertEquals(3.0, median.findMedianSortedArrays(new int[]{}, new int[]{2,3,4}));
        assertEquals(3.0, median.findMedianSortedArrays(new int[]{1,2,3,4,5}, new int[]{}));
        assertEquals(3.0, median.findMedianSortedArrays(new int[]{1,2,3,4,5,6}, new int[]{2}));
        assertEquals(3.0, median.findMedianSortedArrays(new int[]{2}, new int[]{1,2,3,4,5,6}));
        assertEquals(2.0, median.findMedianSortedArrays(new int[]{2}, new int[]{}));
        assertEquals(2.0, median.findMedianSortedArrays(new int[]{1,3}, new int[]{2}));
        assertEquals(2.5, median.findMedianSortedArrays(new int[]{1,2}, new int[]{3,4}));
        assertEquals(-1.0, median.findMedianSortedArrays(new int[]{3}, new int[]{-2, -1}));
    }
}