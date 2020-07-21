package com.atu.capital.common;

/**
 * @author: Tom
 * @date: 2020-07-21 17:35
 * @description:
 */
public class InsufficientBalanceException extends RuntimeException {
    private static final long serialVersionUID = 6689953065473521009L;

    public InsufficientBalanceException() {

    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
