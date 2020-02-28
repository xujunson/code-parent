package com.tom.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author: Tom
 * @date: 2020-02-27 20:14
 * @description:
 */
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDBPass(String input, String saltDB) {
        String fromPass = inputPassToFormPass(input);
        String dbPass = formPassToDBPass(fromPass, saltDB);
        return dbPass;
    }
}
