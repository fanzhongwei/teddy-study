package com.teddy.leetcode.a5最长回文字子串;

/**
 * <a href="https://leetcode.cn/problems/longest-palindromic-substring/">最长回文子串</a>
 *
 * @author fanzhongwei
 * @date 2023/03/28 13:58
 **/
public class LongestPalindrome {

    public String longestPalindrome(String s) {
        // 只有一个字符也是回文串
        if (s.length() == 1) {
            return s;
        }
        int head = 0;
        int tail = s.length() - 1;
        int headPos = head;
        int tailPos = tail;
        String result = s.substring(0, 1);
        while (true) {
            // 如果headPos >= tailPos的时候，就表示整个字符串都搜索完成了
            if (headPos >= tailPos) {
                break;
            }
            boolean goNext = false;
            // 对比子串头尾，相等的情况下
            if (s.charAt(head) == s.charAt(tail)) {
                // head、tail距离为1|0的时候，就表示找到了回文字串
                if (tail - head <= 1) {
                    result = result.length() > tailPos - headPos + 1 ? result : s.substring(headPos, tailPos + 1);
                    goNext = true;
                } else {
                    head++;
                    tail--;
                }
            } else {
                // headPos、tail距离为1|0的时候，表示headPos位置所有的可能性已经找完，继续下一轮寻找
                if (tail - headPos <= 1) {
                    goNext = true;
                } else {
                    // 子串中间字符不相等的情况，head复位，移动tail到tailPos前一位继续寻找
                    head = headPos;
                    tail = --tailPos;
                }
                if (tailPos - headPos + 1 <= result.length()) {
                    goNext = true;
                }
            }
            if (goNext) {
                // 继续下一轮寻找
                head = ++headPos;
                tail = tailPos = s.length() - 1;
                // 如果s(headPos, tailPos).length() <= result.length()，就没有必要再找了
                if (tailPos - headPos + 1 <= result.length()) {
                    break;
                }
            }
        }

        return result;
    }
}
