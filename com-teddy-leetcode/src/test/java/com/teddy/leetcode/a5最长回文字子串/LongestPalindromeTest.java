package com.teddy.leetcode.a5最长回文字子串;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestPalindromeTest {

    @Test
    void longestPalindrome() {
        LongestPalindrome test = new LongestPalindrome();
        assertTrue("bab".equals(test.longestPalindrome("babad")) || "aba".equals(test.longestPalindrome("babad")));
        assertEquals("bb", test.longestPalindrome("cbbd"));
        assertEquals("bacab", test.longestPalindrome("bacabab"));
        assertEquals("aaabaaa", test.longestPalindrome("aaabaaaa"));
    }
}