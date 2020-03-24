package com.atu.imooccache;

import com.atu.imooccache.computable.Computable;
import com.atu.imooccache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Tom
 * @date: 2020-03-24 17:28
 * @description: 使用ConcurrentHashMap优化缓存，提高性
 */
public class ImoocCache5<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache5(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        System.out.println("进入缓存机制");
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ImoocCache5<String, Integer> expensiveComputer = new ImoocCache5<>(new ExpensiveFunction());
        Integer result = expensiveComputer.compute("666");
        System.out.println("第一次计算结果：" + result);

        result = expensiveComputer.compute("666");
        System.out.println("第二次计算结果：" + result);
    }

}
