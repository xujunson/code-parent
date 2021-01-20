package com.atu.threadlocal.reference;

import java.util.WeakHashMap;

/**
 * @author: Tom
 * @date: 2021-01-16 10:12
 * @description:
 */
public class WeakHashMapTest {
    public static void main(String[] args) {
        House seller1 = new House("1号卖家房源");
        SellerInfo sellerInfo1 = new SellerInfo();

        House seller2 = new House("2号卖家房源");
        SellerInfo sellerInfo2 = new SellerInfo();

        //如果换成HashMap 则Key是对House的强引用
        WeakHashMap<House, SellerInfo> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(seller1, sellerInfo1);
        weakHashMap.put(seller2, sellerInfo2);
        System.out.println("weakHashMap before null, size = " + weakHashMap.size());

        seller1 = null;
        System.gc();
        System.runFinalization();

        System.out.println("weakHashMap after null, size = " + weakHashMap.size());
        System.out.println(weakHashMap);
    }
}

class SellerInfo {

}
