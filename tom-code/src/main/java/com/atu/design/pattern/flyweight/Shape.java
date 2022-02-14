package com.atu.design.pattern.flyweight;

/**
 * @Author: Tom
 * @Date: 2022/2/14 15:52
 * @Description:
 */

/**
 * 抽象享元类
 * 这里以画图形举例：比如画圆，加入颜色固定，画圆的方式都是一样的，所不同的就是圆形的位置和圆的半径
 */
interface Shape {
    public void draw();
}
