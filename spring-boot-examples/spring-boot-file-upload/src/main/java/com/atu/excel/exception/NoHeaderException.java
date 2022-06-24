package com.atu.excel.exception;

/**
 * 没有表头exception
 **/
public class NoHeaderException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoHeaderException() {
        super();
    }

    public NoHeaderException(String message) {
        super(message);
    }
}
