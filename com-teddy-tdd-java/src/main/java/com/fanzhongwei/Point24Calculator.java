package com.fanzhongwei;

import java.util.Arrays;
import java.util.List;

/**
 * 24点计算器
 *
 * @author fanzhongwei
 * @date 2019年7月4日 下午4:49:23
 * @version 1.0.0
 */
public class Point24Calculator {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("必须输入四个参数才能计算。");
            return;
        }
        int[] numbers = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                numbers[i] = Integer.parseInt(args[i]);
                if (numbers[i] <= 0 || numbers[i] > 10) {
                    System.out.println("参数：" + args[i] + " 超出范围:(0,10]。");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("参数：" + args[i] + "不是数字，请检查。");
                return;
            }
        }
        find(24, numbers);
    }

    /**
     * 找到任意数字集合计算结果为point的组合
     *
     * @param point 计算的结果值
     * @param numbers 数字集合
     * @author fanzhongwei
     * @date 2019-07-07 16:36:07
     * @version 1.0.0
     */
    static void find(int point, int... numbers) {
        List<CalculatorExpression> result = new Calculator(point).calc(numbers);
        System.out.println(Arrays.toString(numbers) + "一共找到" + result.size() + "种解法。");
        if (null == result || result.isEmpty()) {
            return;
        }
        for (CalculatorExpression expression : result) {
            System.out.println(expression.getExpression());
        }
    }
}
