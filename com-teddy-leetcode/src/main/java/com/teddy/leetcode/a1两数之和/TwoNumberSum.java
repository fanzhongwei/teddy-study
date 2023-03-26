package com.teddy.leetcode.a1两数之和;

import java.util.*;

/**
 * 两数之和
 *
 * @author fanzhongwei
 * @date 2023/03/07 19:38
 **/
public class TwoNumberSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> possibleMap = new HashMap<>();
        int a;
        Integer index;
        for (int i = 0; i < nums.length; i++) {
            // 存在两数相同的情况
            index = possibleMap.put(nums[i], i);
            if (null != index && nums[i] + nums[i] == target) {
                return new int[]{index, i};
            }
            // nums[i] + a = target
            a = target - nums[i];
            // 如果存在解，则必然会有一个数为a
            index = possibleMap.get(a);
            if (null != index && index != i) {
                return new int[]{index, i};
            }
        }
        throw new IllegalArgumentException("输入不合法，无法找到两数之和=" + target);
    }


}
