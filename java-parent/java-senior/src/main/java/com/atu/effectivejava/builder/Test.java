package com.atu.effectivejava.builder;

import static com.atu.effectivejava.builder.NyPizza.Size.SMALL;
import static com.atu.effectivejava.builder.Pizza.Topping.HAM;

/**
 * @Author: Tom
 * @Date: 2021/10/14 7:45 下午
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(SMALL).addTopping(Pizza.Topping.SAUSAGE).addTopping(Pizza.Topping.ONION).build();

        Calzone calzone =new Calzone.Builder().addTopping(HAM).sauceInside().build();
    }
}
