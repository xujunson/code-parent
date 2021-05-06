package com.atu.design.predicate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Tom
 * @Date: 2021/5/6 11:00 上午
 * @Description: 过滤类
 */
public class Filters {
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (null != t && predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
