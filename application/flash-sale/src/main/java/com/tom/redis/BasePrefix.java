package com.tom.redis;

/**
 * @author: Tom
 * @date: 2020-02-27 16:36
 * @description: 简单实现
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireSeconds;
    private String prefix;

    //抽象类 不可以被new
    public BasePrefix(String prefix) { //默认0代表永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        //防止重复
        String className = getClass().getSimpleName();
        return className + ":" + prefix;
    }
}


