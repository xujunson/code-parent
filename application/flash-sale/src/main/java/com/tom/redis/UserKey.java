package com.tom.redis;

/**
 * @author: Tom
 * @date: 2020-02-27 16:39
 * @description:
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
}
