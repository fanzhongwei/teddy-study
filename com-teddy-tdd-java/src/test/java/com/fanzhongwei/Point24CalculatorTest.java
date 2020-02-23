package com.fanzhongwei;

import org.junit.Assert;
import org.junit.Test;

public class Point24CalculatorTest {

    @Test
    public void calc(){
        Point24Calculator.main(new String[]{"1","2","3","4"});
    }

    @Test
    public void test_calc_25(){
        Point24Calculator.find(25, 1,2,3,5);
    }

    @Test
    public void test_5_numbers_calc_24(){
        Point24Calculator.find(24, 200,100,105,101,220);
    }

    @Test
    public void test_4_numbers_has_15_groups(){
        Assert.assertEquals(15, new Calculator(24).group(new int[]{1,2,3,4}).size());
    }

    @Test
    public void test_5_numbers_has_31_groups(){
        Assert.assertEquals(31, new Calculator(24).group(new int[]{1,2,3,4,5}).size());
    }
}