package com.atu.design.pattern.flyweight;

/**
 * @Author: Tom
 * @Date: 2022/2/14 15:52
 * @Description:
 */

/**
 * 具体享元类
 * 这里创建具体的享元类，类中包含了可以共享的数据和不可共享的数据
 * 例如：可以共享的颜色以及隐形的画圆方式，不可共享的半径和坐标
 */
class Circle implements Shape {

    private int x;
    private int y;
    private double radius;
    private String color;

    public Circle(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void draw() {
        System.out.println("画了一个圆心坐标为：(" + this.x + "," + this.y + "),半径为" + this.radius + "," + this.color + "的圆");
    }

}
