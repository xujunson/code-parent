package com.atu.stopthreads;

/**
 * @author: Tom
 * @date: 2020-03-09 16:50
 * @description: run无法抛出checked Exception，只能用 try/catch
 */
public class RunThrowException {
    public void aVoid() throws Exception {
        throw new Exception();
    }

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    throw new Exception();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
