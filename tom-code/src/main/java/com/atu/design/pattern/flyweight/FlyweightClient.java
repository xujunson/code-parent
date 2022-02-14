package com.atu.design.pattern.flyweight;

import java.util.Random;

/**
 * @Author: Tom
 * @Date: 2022/2/14 15:49
 * @Description:
 */
public class FlyweightClient {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Circle circle = new Circle(getColor());
            circle.setRadius(getRadius());
            circle.setX(getZ());
            circle.setY(getZ());
            circle.draw();
        }
    }

    public static String getColor() {
        String[] colors = {"红色", "橙色", "黄色", "青色", "绿色"};
        Random random = new Random();
        int index = random.nextInt(4);
        return colors[index];
    }

    public static double getRadius() {
        Random random = new Random();
        return random.nextDouble() * 20;
    }

    public static int getZ() {
        Random random = new Random();
        return random.nextInt(100);
    }
}