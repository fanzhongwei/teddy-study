package com.fanzhongwei;

import java.util.Objects;

/**
 * 计算结果的表达式：1＋2=3
 *
 * @author fanzhongwei
 * @date 2019年7月4日 下午4:49:23
 * @version 1.0.0
 */
public final class CalculatorExpression {

    private static final String ADD_OPERATOR = "＋";
    private static final String SUB_OPERATOR = "－";
    private static final String MUL_OPERATOR = "×";
    private static final String DIV_OPERATOR = "÷";

    /** 用来抵消浮点数比较中因为误差造成无法判断相等的情况，精度可以自行选择，这里选的0.000001 */
    private static final double THRESHOLD = 1E-6;

    private String operatorType;
    private final String expression;
    private final double result;

    /**
     * 构造一个计算结果
     *
     * @param expression 表达式
     * @param result 运算结果
     * @author fanzhongwei
     * @date 2019-07-07 15:59:14
     * @version 1.0.0
     */
    private CalculatorExpression(String expression, double result) {
        this.expression = expression;
        this.result = result;
    }

    /**
     * 计算两个表达式相加的结果
     *
     * @param a 表达式a
     * @param b 表达式b
     * @return com.fanzhongwei.CalculatorExpression
     * @author fanzhongwei
     * @date 2019-07-07 16:00:56
     * @version 1.0.0
     */
    static CalculatorExpression add(CalculatorExpression a, CalculatorExpression b) {
        return build(a.getExpression() + ADD_OPERATOR + b.getExpression(), a.getResult() + b.getResult()).operatorType(ADD_OPERATOR);
    }

    /**
     * 计算两个表达式相减的结果
     *
     * @param a 表达式a
     * @param b 表达式b
     * @return com.fanzhongwei.CalculatorExpression
     * @author fanzhongwei
     * @date 2019-07-07 16:00:56
     * @version 1.0.0
     */
    static CalculatorExpression sub(CalculatorExpression a, CalculatorExpression b) {
        // 减数如果是加法或者减法的话需要加括号，比如：1-2-2 != 1-(2-2)
        if (Objects.equals(ADD_OPERATOR, b.getOperatorType()) || Objects.equals(SUB_OPERATOR, b.getOperatorType())) {
            return build(a.getExpression() + SUB_OPERATOR + getWarpBarcketsExpression(b), a.getResult() - b.getResult())
                .operatorType(SUB_OPERATOR);
        }
        return build(a.getExpression() + SUB_OPERATOR + b.getExpression(), a.getResult() - b.getResult()).operatorType(SUB_OPERATOR);
    }

    /**
     * 计算两个表达式相乘的结果
     *
     * @param a 表达式a
     * @param b 表达式b
     * @return com.fanzhongwei.CalculatorExpression
     * @author fanzhongwei
     * @date 2019-07-07 16:00:56
     * @version 1.0.0
     */
    static CalculatorExpression mul(CalculatorExpression a, CalculatorExpression b) {
        return build(getMulExpression(a) + MUL_OPERATOR + getMulExpression(b), a.getResult() * b.getResult()).operatorType(MUL_OPERATOR);
    }

    private static String getMulExpression(CalculatorExpression expression) {
        // 如果表达式是加减法的话，就加上括号
        if (Objects.equals(ADD_OPERATOR, expression.operatorType) || Objects.equals(SUB_OPERATOR, expression.operatorType)) {
            return getWarpBarcketsExpression(expression);
        }
        return expression.getExpression();
    }

    /**
     * 计算两个表达式相除的结果
     *
     * @param a 表达式a
     * @param b 表达式b
     * @return com.fanzhongwei.CalculatorExpression
     * @author fanzhongwei
     * @date 2019-07-07 16:00:56
     * @version 1.0.0
     */
    static CalculatorExpression div(CalculatorExpression a, CalculatorExpression b) {
        return build(getDivExpression(a, b), a.getResult() / b.getResult()).operatorType(DIV_OPERATOR);
    }

    private static String getDivExpression(CalculatorExpression a, CalculatorExpression b) {
        StringBuilder sb = new StringBuilder();
        // 被除数只为乘法或除法的时候才不用加括号：1×2÷2 = (1×2)÷2
        // operatorType 为空的时候表示只有一个数字
        if (Objects.equals(MUL_OPERATOR, a.getOperatorType()) || Objects.equals(DIV_OPERATOR, a.getOperatorType()) || null == a.getOperatorType()) {
            sb.append(a.getExpression());
        } else {
            sb.append(getWarpBarcketsExpression(a));
        }
        sb.append(DIV_OPERATOR);
        // 除数只要是表达式都要加括号
        // operatorType 为空的时候表示只有一个数字
        if (null == b.getOperatorType()) {
            sb.append(b.getExpression());
        } else {
            sb.append(getWarpBarcketsExpression(b));
        }
        return sb.toString();
    }

    private static String getWarpBarcketsExpression(CalculatorExpression expression) {
        return "(" + expression.getExpression() + ")";
    }

    /**
     * 构造一个只有一个数字的表达式
     *
     * @param number 集合中只有一个元素，那么该表达式只有一个数字
     * @return com.fanzhongwei.CalculatorExpression
     * @author fanzhongwei
     * @date 2019-07-07 16:04:43
     * @version 1.0.0
     */
    static CalculatorExpression from(int number) {
        return build(String.valueOf(number), number);
    }

    /**
     * 构造一个表达式
     *
     * @param expression 表达式
     * @param result 表达式的结果
     * @return com.fanzhongwei.CalculatorExpression
     * @author fanzhongwei
     * @date 2019-07-07 16:07:36
     * @version 1.0.0
     */
    static CalculatorExpression build(String expression, double result) {
        return new CalculatorExpression(expression, result);
    }

    private CalculatorExpression operatorType(String type) {
        this.operatorType = type;
        return this;
    }

    private String getOperatorType() {
        return this.operatorType;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof CalculatorExpression)) {
            return false;
        }
        CalculatorExpression calculatorExpression = (CalculatorExpression)obj;
        return Objects.equals(this.expression, calculatorExpression.getExpression())
            && Math.abs(this.result - calculatorExpression.getResult()) < THRESHOLD;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.expression, this.result);
    }

    @Override
    public String toString() {
        return this.expression + "=" + this.result;
    }

    /**
     * 获取表达式
     *
     * @return java.lang.String
     * @author fanzhongwei
     * @date 2019-07-07 16:11:28
     * @version 1.0.0
     */
    String getExpression() {
        return this.expression;
    }

    /**
     * 获取表达式的计算结果
     *
     * @return double
     * @author fanzhongwei
     * @date 2019-07-07 16:11:05
     * @version 1.0.0
     */
    double getResult() {
        return this.result;
    }

    /**
     * 表达式是否存在，浮点数的计算可能有NaN、INFINITY的情况
     *
     * @return boolean
     * @author fanzhongwei
     * @date 2019-07-07 16:09:32
     * @version 1.0.0
     */
    boolean isExist() {
        // 除数不为0，并且计算的结果是存在的
        return Double.isFinite(this.result) && !Double.isNaN(this.result);
    }

}
