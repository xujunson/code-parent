package com.tom.redis;

/**
 * @author: Tom
 * @date: 2020-02-27 16:39
 * @description:
 */
public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}
