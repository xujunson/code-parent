package com.tom.redis;

/**
 * @author: Tom
 * @date: 2020-02-27 16:39
 * @description:
 */
public class OrderKey extends BasePrefix {
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}
