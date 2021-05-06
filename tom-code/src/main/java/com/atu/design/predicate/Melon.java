package com.atu.design.predicate;

/**
 * @Author: Tom
 * @Date: 2021/5/6 11:05 上午
 * @Description:
 */
public class Melon {
    /**
     * 品种
     */

    private final String type;
    /**
     * 重量
     */
    private final int weight;

    /**
     * 产地
     */
    private final String origin;

    public String getType() {
        return type;
    }

    public int getWeight() {
        return weight;
    }

    public String getOrigin() {
        return origin;
    }

    public Melon(String type, int weight, String origin) {
        this.type = type;
        this.weight = weight;
        this.origin = origin;
    }

    @Override
    public String toString() {
        return "Melon{" +
                "type='" + type + '\'' +
                ", weight=" + weight +
                ", origin='" + origin + '\'' +
                '}';
    }
}
