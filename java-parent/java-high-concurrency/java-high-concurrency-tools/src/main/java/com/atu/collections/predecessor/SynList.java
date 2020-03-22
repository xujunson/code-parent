package com.atu.collections.predecessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-22 10:13
 * @description: 演示Collections.synchronizedList(new ArrayList < E > ())
 */
public class SynList {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        list.add(5);
        System.out.println(list.get(0));
    }
}
