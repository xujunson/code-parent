package com.atu.ad.exception;

/**
 * @author: Tom
 * @date: 2020-03-26 20:17
 * @description: 定义统一异常处理类
 */
public class AdException extends Exception {
    public AdException(String message) {
        super(message);
    }
}
