package com.teddy.leetcode.a3无重复最长子串;

import java.util.HashSet;
import java.util.Set;

public class NoRepeatStrLength {

    public int lengthOfLongestSubstring(String s) {
        Set<Character> noRepeat = new HashSet<>();
        int max = 0;
        int startIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            // 找到重复的
            char c = s.charAt(i);
            if (noRepeat.contains(c)) {
                // 记录当前最大子串长度
                max = Math.max(noRepeat.size(), max);
                // 跳过重复字符及其之前的字符，继续下一轮寻找
                while (startIndex <= i) {
                    char o = s.charAt(startIndex);
                    startIndex++;
                    noRepeat.remove(o);
                    if (o == c) {
                        break;
                    }
                }
                noRepeat.add(c);
            }
            noRepeat.add(c);
        }
        return Math.max(noRepeat.size(), max);
    }
}
