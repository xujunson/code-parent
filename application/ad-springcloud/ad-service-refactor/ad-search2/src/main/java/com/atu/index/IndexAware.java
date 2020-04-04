package com.atu.index;

/**
 * @author: Tom
 * @date: 2020-03-29 10:11
 * @description: K——索引的键，V——返回值
 */
public interface IndexAware<K, V> {
    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);
}
