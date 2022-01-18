package com.atu.effectivejava.extendshort;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @Author: Tom
 * @Date: 2021/11/9 23:47
 * @Description: 复合优于继承
 */
public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;

    public InstrumentedHashSet() {}

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount+= c.size();
        return super.addAll(c);
    }
    public int getAddCount() {
        return addCount;
    }


    public static void main(String[] args) {
        InstrumentedHashSet<String> s = new InstrumentedHashSet<String>();
        s.addAll(Arrays.asList("Snap","Crackle","Pop"));
        System.out.println(s.getAddCount()); //6 是错误的
    }
}
