package com.fanzhongwei;

import static com.fanzhongwei.CalculatorExpression.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * package com.fanzhongwei
 * description: CalculatorExpressionTest
 * Copyright 2019 teddy, Inc. All rights reserved.
 *
 * @author fanzhongwei
 * @date 2019-07-03 19:55:34
 */
public class CalculatorExpressionTest {

    @Test
    public void test_expression_equals(){
        Assert.assertEquals(build("0.1", 0.1d), build("0.1", 0.1d));
    }

    @Test
    public void test_1_2_sub_expression(){
        Assert.assertEquals(build("1－2", -1), sub(from(1),from(2)));
    }

    @Test
    public void test_1_2_mul_expression(){
        CalculatorExpression expression = build("1×2", 2);
        Assert.assertEquals(expression, mul(from(1),from(2)));
    }

    @Test
    public void test_1_2_div_expression(){
        Assert.assertEquals(build("1÷2", 0.5), div(from(1),from(2)));
    }

    @Test
    public void test_number_and_expression_add(){
        // 加法 + number
        CalculatorExpression expression = add(from(1), from(2));
        Assert.assertEquals(build("3＋1＋2", 6), add(from(3), expression));

        // 减法 + number
        expression = sub(from(1), from(2));
        Assert.assertEquals(build("3＋1－2", 2), add(from(3), expression));

        // 乘法 + number
        expression = mul(from(1), from(2));
        Assert.assertEquals(build("3＋1×2", 5), add(from(3), expression));

        // 除法 + number
        expression = div(from(1), from(2));
        Assert.assertEquals(build("3＋1÷2", 3.5), add(from(3), expression));
    }

    @Test
    public void test_number_and_expression_sub() {
        // 加法 - number
        CalculatorExpression expression = add(from(1), from(2));
        Assert.assertEquals(build("1＋2－3", 0), sub(expression, from(3)));
        Assert.assertEquals(build("3－(1＋2)", 0), sub(from(3), expression));

        // 减法 - number
        expression = sub(from(1), from(2));
        Assert.assertEquals(build("1－2－3", -4), sub(expression, from(3)));
        Assert.assertEquals(build("3－(1－2)", 4), sub(from(3), expression));

        // 乘法 - number
        expression = mul(from(1), from(2));
        Assert.assertEquals(build("1×2－3", -1), sub(expression, from(3)));
        Assert.assertEquals(build("3－1×2", 1), sub(from(3), expression));

        // 除法 - number
        expression = div(from(1), from(2));
        Assert.assertEquals(build("1÷2－3", -2.5), sub(expression, from(3)));
        Assert.assertEquals(build("3－1÷2", 2.5), sub(from(3), expression));
    }

    @Test
    public void test_number_and_expression_mul() {
        // 加法 * number
        CalculatorExpression expression = add(from(1), from(2));
        Assert.assertEquals(build("3×(1＋2)", 9), mul(from(3), expression));

        // 减法 * number
        expression = sub(from(1), from(2));
        Assert.assertEquals(build("3×(1－2)", -3), mul(from(3), expression));

        // 乘法 * number
        expression = mul(from(1), from(2));
        Assert.assertEquals(build("3×1×2", 6), mul(from(3), expression));

        // 除法 * number
        expression = div(from(1), from(2));
        Assert.assertEquals(build("3×1÷2", 1.5), mul(from(3), expression));
    }

    @Test
    public void test_number_and_expression_div() {
        // 加法 ÷ number
        CalculatorExpression expression = add(from(1), from(2));
        Assert.assertEquals(build("(1＋2)÷2", 1.5), div(expression, from(2)));
        Assert.assertEquals(build("6÷(1＋2)", 2), div(from(6), expression));

        // 减法 ÷ number
        expression = sub(from(1), from(2));
        Assert.assertEquals(build("(1－2)÷2", -0.5), div(expression, from(2)));
        Assert.assertEquals(build("2÷(1－2)", -2), div(from(2), expression));

        // 乘法 ÷ number
        expression = mul(from(4), from(2));
        Assert.assertEquals(build("4×2÷2", 4), div(expression, from(2)));
        Assert.assertEquals(build("8÷(4×2)", 1), div(from(8), expression));

        // 除法 ÷ number
        expression = div(from(1), from(2));
        Assert.assertEquals(build("1÷2÷2", 0.25), div(expression, from(2)));
        Assert.assertEquals(build("2÷(1÷2)", 4), div(from(2), expression));
    }

    @Test
    public void test_to_expression_add(){
        // 加法 + 加法
        CalculatorExpression expression1 = add(from(1), from(2));
        CalculatorExpression expression2 = add(from(1), from(2));
        Assert.assertEquals(build("1＋2＋1＋2", 6), add(expression1, expression2));

        // 加法 + 减法
        expression1 = add(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("1＋2＋1－2", 2), add(expression1, expression2));

        // 加法 + 乘法
        expression1 = add(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("1＋2＋1×2", 5), add(expression1, expression2));

        // 加法 + 除法
        expression1 = add(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1＋2＋1÷2", 3.5), add(expression1, expression2));

        // 减法 + 减法
        expression1 = sub(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("1－2＋1－2", -2), add(expression1, expression2));

        // 减法 + 乘法
        expression1 = sub(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("1－2＋1×2", 1), add(expression1, expression2));

        // 减法 + 除法
        expression1 = sub(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1－2＋1÷2", -0.5), add(expression1, expression2));

        // 乘法 + 乘法
        expression1 = mul(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("1×2＋1×2", 4), add(expression1, expression2));

        // 乘法 + 除法
        expression1 = mul(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1×2＋1÷2", 2.5), add(expression1, expression2));

        // 除法 + 除法
        expression1 = div(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1÷2＋1÷2", 1), add(expression1, expression2));
    }

    @Test
    public void test_to_expression_sub(){
        // 加法 － 加法
        CalculatorExpression expression1 = add(from(1), from(2));
        CalculatorExpression expression2 = add(from(1), from(2));
        Assert.assertEquals(build("1＋2－(1＋2)", 0), sub(expression1, expression2));

        // 加法 － 减法
        expression1 = add(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("1＋2－(1－2)", 4), sub(expression1, expression2));

        // 加法 － 乘法
        expression1 = add(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("1＋2－1×2", 1), sub(expression1, expression2));

        // 加法 － 除法
        expression1 = build("1＋2", 3);
        expression2 = build("1÷2", 0.5);
        Assert.assertEquals(build("1＋2－1÷2", 2.5), sub(expression1, expression2));

        // 减法 － 减法
        expression1 = sub(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("1－2－(1－2)", 0), sub(expression1, expression2));

        // 减法 － 乘法
        expression1 = sub(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("1－2－1×2", -3), sub(expression1, expression2));

        // 减法 － 除法
        expression1 = sub(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1－2－1÷2", -1.5), sub(expression1, expression2));

        // 乘法 － 乘法
        expression1 = mul(from(1), from(2));
        expression2 = mul(from(2), from(2));
        Assert.assertEquals(build("1×2－2×2", -2), sub(expression1, expression2));

        // 乘法 － 除法
        expression1 = mul(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1×2－1÷2", 1.5), sub(expression1, expression2));

        // 除法 － 除法
        expression1 = div(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1÷2－1÷2", 0), sub(expression1, expression2));
    }

    @Test
    public void test_to_expression_mul(){
        // 加法 * 加法
        CalculatorExpression expression1 = add(from(1), from(2));
        CalculatorExpression expression2 = add(from(1), from(2));
        Assert.assertEquals(build("(1＋2)×(1＋2)", 9), mul(expression1, expression2));

        // 加法 * 减法
        expression1 = add(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("(1＋2)×(1－2)", -3), mul(expression1, expression2));

        // 加法 * 乘法
        expression1 = add(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("(1＋2)×1×2", 6), mul(expression1, expression2));

        // 加法 * 除法
        expression1 = add(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("(1＋2)×1÷2", 1.5), mul(expression1, expression2));

        // 减法 * 减法
        expression1 = sub(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("(1－2)×(1－2)", 1), mul(expression1, expression2));

        // 减法 * 乘法
        expression1 = sub(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("(1－2)×1×2", -2), mul(expression1, expression2));

        // 减法 * 除法
        expression1 = sub(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("(1－2)×1÷2", -0.5), mul(expression1, expression2));

        // 乘法 * 乘法
        expression1 = mul(from(1), from(2));
        expression2 = mul(from(2), from(2));
        Assert.assertEquals(build("1×2×2×2", 8), mul(expression1, expression2));

        // 乘法 * 除法
        expression1 = mul(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1×2×1÷2", 1), mul(expression1, expression2));

        // 除法 * 除法
        expression1 = div(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1÷2×1÷2", 0.25), mul(expression1, expression2));
    }

    @Test
    public void test_to_expression_div(){
        // 加法 ÷ 加法
        CalculatorExpression expression1 = add(from(1), from(2));
        CalculatorExpression expression2 = add(from(1), from(2));
        Assert.assertEquals(build("(1＋2)÷(1＋2)", 1), div(expression1, expression2));

        // 加法 ÷ 减法
        expression1 = add(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("(1＋2)÷(1－2)", -3), div(expression1, expression2));

        // 加法 ÷ 乘法
        expression1 = add(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("(1＋2)÷(1×2)", 1.5), div(expression1, expression2));

        // 加法 ÷ 除法
        expression1 = add(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("(1＋2)÷(1÷2)", 6), div(expression1, expression2));

        // 减法 ÷ 减法
        expression1 = sub(from(1), from(2));
        expression2 = sub(from(1), from(2));
        Assert.assertEquals(build("(1－2)÷(1－2)", 1), div(expression1, expression2));

        // 减法 ÷ 乘法
        expression1 = sub(from(1), from(2));
        expression2 = mul(from(1), from(2));
        Assert.assertEquals(build("(1－2)÷(1×2)", -0.5), div(expression1, expression2));

        // 减法 ÷ 除法
        expression1 = sub(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("(1－2)÷(1÷2)", -2), div(expression1, expression2));

        // 乘法 ÷ 乘法
        expression1 = mul(from(1), from(2));
        expression2 = mul(from(2), from(2));
        Assert.assertEquals(build("1×2÷(2×2)", 0.5), div(expression1, expression2));

        // 乘法 ÷ 除法
        expression1 = mul(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1×2÷(1÷2)", 4), div(expression1, expression2));

        // 除法 ÷ 除法
        expression1 = div(from(1), from(2));
        expression2 = div(from(1), from(2));
        Assert.assertEquals(build("1÷2÷(1÷2)", 1), div(expression1, expression2));
    }

    @Test
    public void test_div_has_0_expression(){
        Assert.assertFalse(div(from(3), sub(from(1),from(1))).isExist());
        Assert.assertFalse(div(sub(from(1),from(1)), from(0)).isExist());
        Assert.assertFalse(div(div(from(1),from(3)), sub(from(1),from(1))).isExist());
        Assert.assertFalse(div(from(0),from(0)).isExist());
        Assert.assertFalse(div(sub(from(1),from(1)),sub(from(1),from(1))).isExist());
    }
}
