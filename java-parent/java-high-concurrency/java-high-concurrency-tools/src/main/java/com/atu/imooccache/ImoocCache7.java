package com.atu.imooccache;

import com.atu.imooccache.computable.Computable;
import com.atu.imooccache.computable.ExpensiveFunction;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author: Tom
 * @date: 2020-03-24 17:28
 * @description: 利用Future，避免重复计算
 */
public class ImoocCache7<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();

    private final Computable<A, V> c;

    public ImoocCache7(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws Exception {
        Future<V> f = cache.get(arg);
        if (f == null) {
            //任务
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return c.compute(arg);
                }
            };
            FutureTask<V> ft = new FutureTask<>(callable);
            f = ft;

            //在计算之前，把ft放到缓存中，好处就是一旦第一个线程放进缓存中
            //第二个在cache.get(arg)的时候，由于ConcurrentHashMap可见性保证，就会立刻看到
            // f肯定不为空
            cache.put(arg, ft);
            System.out.println("从FutureTask调用了计算函数");
            ft.run(); //计算
        }
        return f.get();
    }

    public static void main(String[] args) throws Exception {
        ImoocCache7<String, Integer> expensiveComputer = new ImoocCache7<>(new ExpensiveFunction());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("666");

                    System.out.println("第一次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("667");
                    System.out.println("第二次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Integer result = expensiveComputer.compute("666");

                    System.out.println("第三次的计算结果：" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
