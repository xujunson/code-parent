package com.atu.immutable;

/**
 * @author: Tom
 * @date: 2020-03-18 17:01
 * @description: final的方法
 */
public class FinalMethodDemo {
    public void drink() {

    }

    public final void eat() {

    }

    public static void sleep() {

    }
}

class SubClass extends FinalMethodDemo {
    @Override
    public void drink() {
        super.drink();
    }
    public static void sleep() {

    }
}
