package com.teddy.leetcode.a4两数组中位数;

/**
 * <a href="https://leetcode.cn/problems/median-of-two-sorted-arrays/">寻找两数组的中位数</a>
 *
 * 算法执行时间1ms，击败了100%的人
 *
 * @author fanzhongwei
 * @date 2023/03/27 12:47
 **/
public class ToSortedArrayMedian {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        boolean hasToMedian = (m + n) % 2 == 0;
        if (m == 0) {
            return hasToMedian ? (nums2[n/2] + nums2[n/2 - 1]) / (double)2: nums2[n / 2];
        }
        if (n == 0) {
            return hasToMedian ? (nums1[m/2] + nums1[m/2 - 1]) / (double)2: nums1[m / 2];
        }

        int[] medianArr = new int[(m + n)/2 + 1];
        int medianIndex = 0;
        int indexM = 0;
        int indexN = 0;
        while (true) {
            if (indexM == m) {
                medianArr[medianIndex] = nums2[indexN];
                indexN++;
            } else if (indexN == n) {
                medianArr[medianIndex] = nums1[indexM];
                indexM++;
            }else if (nums1[indexM] < nums2[indexN]) {
                medianArr[medianIndex] = nums1[indexM];
                indexM++;
            } else {
                medianArr[medianIndex] = nums2[indexN];
                indexN++;
            }
            medianIndex++;
            if (medianIndex == medianArr.length) {
                return hasToMedian ? (medianArr[medianIndex - 1] + medianArr[medianIndex - 2]) / (double)2 : medianArr[medianIndex - 1];
            }
        }
    }
}
