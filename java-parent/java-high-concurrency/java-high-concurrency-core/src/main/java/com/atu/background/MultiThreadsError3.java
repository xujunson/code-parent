package com.atu.background;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-12 10:02
 * @description: 发布逸出
 */
public class MultiThreadsError3 {
    private Map<String, String> states;

    public MultiThreadsError3() {
        this.states = new HashMap<>();
        states.put("1", "周一");
        states.put("2", "周二");
        states.put("3", "周三");
        states.put("4", "周四");
        states.put("5", "周五");
        states.put("6", "周六");
        states.put("7", "周日");
    }

    //逸出————导致问题发生
    public Map<String, String> getStates() {
        return states;
    }

    //解决方案————返回副本
    public Map<String, String> getStatesImproved() {
        return new HashMap<>(states);
    }

    public static void main(String[] args) {
        MultiThreadsError3 multiThreadsError3 = new MultiThreadsError3();
        Map<String, String> states = multiThreadsError3.getStates();
        /*System.out.println(states.get("1"));
        states.remove("1");
        System.out.println(states.get("1"));*/
        System.out.println(multiThreadsError3.getStatesImproved().get("1"));
        multiThreadsError3.getStatesImproved().remove("1");
        System.out.println(multiThreadsError3.getStatesImproved().get("1"));
    }
}
