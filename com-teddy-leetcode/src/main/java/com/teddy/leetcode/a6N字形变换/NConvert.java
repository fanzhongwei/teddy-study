package com.teddy.leetcode.a6N字形变换;


import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <a href="https://leetcode.cn/problems/zigzag-conversion/">N字形变换</a>
 *
 * @author fanzhongwei
 * @date 2023/04/29 16:20
 **/
public class NConvert {

    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        StringBuilder[] resultArr = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            resultArr[i] = new StringBuilder(numRows);
        }
        resultArr[0].append(s.charAt(0));
        int index = 1;
        int row = 1;
        boolean toDown = true;
        while(index < s.length()) {
            resultArr[row].append(s.charAt(index));
            index++;
            // 方向变换
            if (row == 0 || row == numRows - 1) {
                toDown = !toDown;
            }
            if (toDown) {
                row++;
            } else {
                row--;
            }
        }
        return Stream.of(resultArr).map(StringBuilder::toString).collect(Collectors.joining());
    }

}
