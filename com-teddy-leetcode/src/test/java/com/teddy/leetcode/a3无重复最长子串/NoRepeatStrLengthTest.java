package com.teddy.leetcode.a3无重复最长子串;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoRepeatStrLengthTest {

    @Test
    void lengthOfLongestSubstring() {
        NoRepeatStrLength tester = new NoRepeatStrLength();
        assertEquals(3, tester.lengthOfLongestSubstring("abcabcbb"));
        assertEquals(1, tester.lengthOfLongestSubstring("bbbbb"));
        assertEquals(3, tester.lengthOfLongestSubstring("pwwkew"));
        assertEquals(1, tester.lengthOfLongestSubstring(" "));
        assertEquals(6, tester.lengthOfLongestSubstring("asjrgapa"));
        assertEquals(3, tester.lengthOfLongestSubstring("aabaab!bb"));
        assertEquals(4, tester.lengthOfLongestSubstring("jbpnbwwd"));
    }
}