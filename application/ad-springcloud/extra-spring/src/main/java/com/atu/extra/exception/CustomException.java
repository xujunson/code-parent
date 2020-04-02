package com.atu.extra.exception;

/**
 * 自定义异常——Checked异常，不会回滚
 */
public class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
    }
}
