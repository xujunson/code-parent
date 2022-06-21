package com.atu.utils;

import org.thymeleaf.expression.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2022/6/21 15:11
 * @Description:
 */
public class BuildSqlUtils {
    public static String buildBatchInsertSql(List<String> lists) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO table (c1) VALUES ");

        for (String str : lists) {
            sb.append("('").append(str).append("', 1, now()),");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        List<String> lists = Arrays.asList("");

        System.out.println(buildBatchInsertSql(lists));

    }
}
