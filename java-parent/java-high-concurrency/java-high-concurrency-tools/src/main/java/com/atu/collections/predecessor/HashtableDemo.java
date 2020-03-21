package com.atu.collections.predecessor;

import java.util.Hashtable;

/**
 * @author: Tom
 * @date: 2020-03-21 18:18
 * @description:
 */
public class HashtableDemo {
    public static void main(String[] args) {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("学完以后跳槽涨薪幅度", "80%");
        System.out.println(hashtable.get("学完以后跳槽涨薪幅度"));
    }
}
