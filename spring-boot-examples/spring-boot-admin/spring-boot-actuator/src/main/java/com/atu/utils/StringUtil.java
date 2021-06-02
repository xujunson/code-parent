package com.atu.utils;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
public class StringUtil {

    private StringUtil() {

    }

    /**
     * 得到摄氏度单位
     *
     * @param value
     * @return
     */
    public static String getTempUnit(Object value) {

        StringBuilder builder = new StringBuilder(String.valueOf(value));
        builder.append("℃");
        return String.valueOf(builder);
    }

    public static String getPercent(double value) {
        String result = value * 100 + "%";

        return result;
    }
}
