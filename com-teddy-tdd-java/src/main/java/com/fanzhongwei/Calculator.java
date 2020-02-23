package com.fanzhongwei;

import static com.fanzhongwei.CalculatorExpression.*;

import java.util.*;

/**
 * 计算器，可以计算任意个数的数字经过四则运算结果等于给定值的所有组合
 *
 * @author fanzhongwei
 * @date 2019年7月4日 下午4:49:23
 * @version 1.0.0
 */
class Calculator {

    /** 用来抵消浮点数比较中因为误差造成无法判断相等的情况，精度可以自行选择，这里选的0.000001 */
    private static final double THRESHOLD = 1E-6;

    private int point;
    private List<Set<CalculatorExpression>> groupsList;

    /**
     * 初始化计算器
     *
     * @param point 要计算的结果点数
     * @author fanzhongwei
     * @date 2019-07-07 14:45:11
     * @version 1.0.0
     */
    Calculator(int point) {
        this.point = point;
    }

    /**
     * 计算给定集合的所有组合
     *
     * @param numbers 输入的数字集合
     * @return java.util.List<com.fanzhongwei.CalculatorExpression>
     * @author fanzhongwei
     * @date 2019-07-07 14:59:20
     * @version 1.0.0
     */
    List<CalculatorExpression> calc(int... numbers) {
        List<Set<CalculatorExpression>> groups = group(numbers);
        Set<CalculatorExpression> expressionList = groups.get(groups.size() - 1);
        List<CalculatorExpression> result = new ArrayList<>();
        for (CalculatorExpression expression : expressionList) {
            if (expression.isExist() && Math.abs(expression.getResult() - point) < THRESHOLD) {
                result.add(expression);
            }
        }
        return result;
    }

    /**
     * 对给定的集合进行分组<br/>
     * 对于n个数的集合{a1,a2,a3,a4...}，用一个n位的二进制数来表示，例如：0001-->{a1}, 0011-->{a1,a2} <br/>
     * 对输入的集合进行拆分，分别计算，思路如下：<br/>
     * f(a1,a2,a3,a4) = fork(f(a1), f(a2,a3,a4)) ∪ fork(f(a2), f(a1,a3,a4)) ∪ ···<br/>
     * f(a2,a3,a4) = fork(f(a2), f(a3, a4)) <br/>
     * f(a1,a3,a4) = fork(f(a1), f(a3, a4)) <br/>
     *
     * 从上面可以发现，各个子集的计算过程中会存在重复的计算：f(a1)、f(a2)、f(a3, a4) <br/>
     * 将这些结果记录到一张表中，后面的重复计算直接获取其结果能够节省很大一部分的计算量
     *
     * @param numbers 数字集合
     * @return java.util.List<java.util.Set<com.fanzhongwei.CalculatorExpression>>
     * @author fanzhongwei
     * @date 2019-07-07 15:02:20
     * @version 1.0.0
     */
    List<Set<CalculatorExpression>> group(int[] numbers) {
        groupsList = new ArrayList<>();
        // 加入一个占位，方便后面计算
        groupsList.add(null);
        // 对于n个数，去除重复的组合，共有 (1 << n) -1个真子集
        for (int i = 1; i <= (1 << numbers.length) - 1; i++) {
            groupsList.add(new HashSet<>());
        }
        // 先初始化只有一个数字的集合
        for (int i = 0; i < numbers.length; i++) {
            groupsList.get(1 << i)
                .add(from(numbers[i]));
        }
        // 针对每一个i，找出所有的组合
        for (int i = 1; i < groupsList.size(); i++) {
            groupsList.set(i, findGroup(i));
        }
        // 去除第一个占位的
        groupsList.remove(0);
        return groupsList;
    }

    private Set<CalculatorExpression> findGroup(int i) {
        // 对于f(i) 的子问题看看是否已经以计算过了，有的话直接返回
        Set<CalculatorExpression> groups = groupsList.get(i);
        if (!groups.isEmpty()) {
            return groups;
        }
        for (int x = 1; x < i; x++) {
            // 只有当x&i == x时，x才为i的子集，此时i-x为i的另一个真子集，x∪i-x = i
            // 例如：i = 1001,x = 0001, i-x = 1000
            if ((x & i) == x) {
                groupsList.get(i)
                    .addAll(fork(findGroup(x), findGroup(i - x)));
            }
        }
        return groups;
    }

    /**
     * 计算两个集合之间进行四则运算的所有可能的结果
     *
     * @param groupListA 集合A
     * @param groupListB 集合B
     * @return 去重后的结果集
     */
    private Set<CalculatorExpression> fork(Set<CalculatorExpression> groupListA, Set<CalculatorExpression> groupListB) {
        Set<CalculatorExpression> result = new HashSet<>();

        CalculatorExpression div;
        for (CalculatorExpression a : groupListA) {
            for (CalculatorExpression b : groupListB) {
                result.add(add(a, b));
                result.add(add(b, a));
                result.add(sub(a, b));
                result.add(sub(b, a));
                result.add(mul(a, b));
                result.add(mul(b, a));
                div = div(a, b);
                if (div.isExist()) {
                    result.add(div);
                }
                div = div(b, a);
                if (div.isExist()) {
                    result.add(div);
                }
            }
        }
        return result;
    }
}
