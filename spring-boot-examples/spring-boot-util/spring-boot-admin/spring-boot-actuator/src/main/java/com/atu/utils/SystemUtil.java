package com.atu.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
public class SystemUtil {
    private SystemUtil() {

    }

    /**
     * 获得ip地址
     *
     * @return
     */
    public static String getIpAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return address.getHostAddress();
    }
}
