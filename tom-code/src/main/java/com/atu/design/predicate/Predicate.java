package com.atu.design.predicate;

/**
 * @Author: Tom
 * @Date: 2021/5/6 10:57 上午
 * @Description: 泛型
 */
@FunctionalInterface
public interface Predicate<T> {
    /**
     * 行为参数化：过滤条件
     *
     * @param t
     * @return
     */
    boolean test(T t);
}
