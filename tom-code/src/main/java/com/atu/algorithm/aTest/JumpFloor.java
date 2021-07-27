package com.atu.algorithm.aTest;

/**
 * @Author: Tom
 * @Date: 2021/7/27 4:10 下午
 * @Description: 跳台阶 递归
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 */
public class JumpFloor {
    /**
     * 示例1
     * 输入：2
     * 返回值：2
     *
     * 示例2
     * 输入：7
     *
     * 返回值：21
     *
     * 这是一道经典的递推题目，你可以想如果青蛙当前在第n级台阶上，那它上一步是在哪里呢？
     *
     * 显然，由于它可以跳1级台阶或者2级台阶，所以它上一步必定在第n-1,或者第n-2级台阶，也就是说它跳上n级台阶的跳法数是跳上n-1和跳上n-2级台阶的跳法数之和。
     *
     * 设跳上i级台阶有f(n)种跳法，则它跳上n级的台阶有f(n) = f(n-1)+f(n-2)种跳法。
     *
     * 然后，我们又思考初始n <= 2的情况，跳上1级台阶只有1种跳法，跳上2级台阶有2种跳法，最终我们得到如下的递推式：
     */
    public static int jumpFloor(Integer n) {
        System.out.println(n);
        if (n == 1) return 1;
        if (n == 2) return 2;
        return jumpFloor(n - 1) + jumpFloor(n - 2);
    }

    public static void main(String[] args) {
        System.out.println(jumpFloor(7));
    }
}
