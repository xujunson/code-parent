package com.atu.design.pattern.flyweight;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: Tom
 * @Date: 2022/2/14 15:53
 * @Description:
 */

/**
 * 工厂类：享元模式的具体体现其实是在这一块得到实现的，在这一块我们可以清楚的了解到共享了哪些属性或者数据
 * 在这里假设圆的颜色是固定的，我们只能画固定的几种颜色的圆
 * 在这里例子中对应的共享数据就应该是对应的颜色属性和隐形的不可见的还原的方式，这个在前面交代过，所有圆的
 * 画的方式是一样的
 */
class CircleFactory {
    private static Map<String, Circle> map = new HashMap<>();

    public static Circle getCircle(String color) {
        Circle c = map.get(color);
        if (c == null) {
            c = new Circle(color);
            map.put(color, c);
            return c;
        }
        return c;
    }
}

