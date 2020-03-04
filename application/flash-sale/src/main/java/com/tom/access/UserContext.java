package com.tom.access;

import com.tom.domain.MiaoshaUser;

/**
 * @author: Tom
 * @date: 2020-03-04 10:51
 * @description:
 */
public class UserContext {
    //ThreadLocal 多线程时 保存线程安全的访问方式,和当前线程绑定,不存在线程安全问题
    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }
}
