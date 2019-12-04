package com.atu.senior.Regular;

/**
 * @Author: Tom
 * @Date: 2019/7/5 17:37
 * @Description: 正则表达式
 */
public class Test {
    public static void main(String[] args) {
        String str = "4092x1863x1461mm";
        String formatDate = str.substring(0, str.length() - 2).toUpperCase();
        System.out.println(formatDate);
        String regex = "^[0-9]{4}[X][0-9]{4}[X][0-9]{4}+$";

        System.out.println(formatDate.matches(regex));
        String[] lwh = formatDate.split("X");

        System.out.println(lwh[0] + "-----" + lwh[1] + "----------" + lwh[2]);
    }
}
