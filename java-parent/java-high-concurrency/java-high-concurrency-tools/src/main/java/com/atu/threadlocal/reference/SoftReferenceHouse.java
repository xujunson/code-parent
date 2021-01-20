package com.atu.threadlocal.reference;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Tom
 * @date: 2021-01-16 9:48
 * @description: 设置 -Xms20m -Xmx20m
 */
public class SoftReferenceHouse {
    public static void main(String[] args) {
        //第一处
        //List<House> houses = new ArrayList<>();
        List<SoftReference> houses = new ArrayList<>();
        int i = 0;
        while (true) {
            //第二处
            //houses.add(new House());
            SoftReference<House> buyer2 = new SoftReference<House>(new House());
            houses.add(buyer2);
            System.out.println("i=" + (++i));
        }
    }
}

class House {
    private static final Integer DOOR_NUMBER = 2000;
    public Door[] doors = new Door[DOOR_NUMBER];
    private String name;

    public House() {

    }

    public House(String name) {
        this.name = name;
    }

    class Door {
    }
}
