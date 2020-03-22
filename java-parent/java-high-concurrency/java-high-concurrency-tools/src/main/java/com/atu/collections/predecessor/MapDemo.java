package com.atu.collections.predecessor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-22 10:46
 * @description: 演示Map的基本用法
 */
public class MapDemo {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        System.out.println(map.isEmpty());
        map.put("东哥", 38);
        map.put("西哥", 28);
        System.out.println(map.keySet());
        System.out.println(map.get("西哥"));
        System.out.println(map.size());
        System.out.println(map.containsKey("东哥"));
        map.remove("东哥");
        System.out.println(map.containsKey("东哥"));
        map.put(null, 28);
        System.out.println(map.get(null));

    }
}
