package com.atu.design.predicate;

/**
 * @Author: Tom
 * @Date: 2021/5/6 10:57 上午
 * @Description: 泛型-函数式接口
 *  FunctionalInterface-标记函数式接口，该注解只能标记在"有且仅有一个抽象方法"的接口上
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
