package com.tom.util;

import java.util.UUID;

/**
 * @author: Tom
 * @date: 2020-02-28 10:05
 * @description:
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
