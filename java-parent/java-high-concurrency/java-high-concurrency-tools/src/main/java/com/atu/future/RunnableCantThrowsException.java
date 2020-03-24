package com.atu.future;

import java.util.concurrent.Future;

/**
 * @author: Tom
 * @date: 2020-03-24 14:25
 * @description: 在run方法中无法抛出checked Exception
 */
public class RunnableCantThrowsException {

    public void add() throws Exception {

    }

    public static void main(String[] args) {
        Runnable runnable = () -> {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
