package com.teddy.leetcode.a6N字形变换;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NConvertTest {

    NConvert convert = new NConvert();

    @Test
    void convert() {
//        assertEquals("PAHNAPLSIIGYIR", convert.convert("PAYPALISHIRING", 3));
//        assertEquals("PINALSIGYAHRPI", convert.convert("PAYPALISHIRING", 4));
//        assertEquals("A", convert.convert("A", 1));
        assertEquals("AB", convert.convert("AB", 1));

    }
}