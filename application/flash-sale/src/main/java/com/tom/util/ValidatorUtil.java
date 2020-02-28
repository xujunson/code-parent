package com.tom.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Tom
 * @date: 2020-02-27 21:09
 * @description:
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src))
            return false;
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}
