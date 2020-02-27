package com.tom.redis;

/**
 * @author: Tom
 * @date: 2020-02-27 16:34
 * @description: 模块区分
 */
public interface KeyPrefix {
    /**
     * 有效期
     */
    public int expireSeconds();

    public String getPrefix();
}
