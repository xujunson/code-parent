package com.atu.config;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @Author: Tom
 * @Date: 2021/10/12 2:30 下午
 * @Description:
 */
public class RedissonBloomFilter {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient client = Redisson.create(config);
        RBloomFilter<String> bloomFilter = client.getBloomFilter("test-bloom-filter");
        // 初始化布隆过滤器，数组长度100W，误判率 1%
        bloomFilter.tryInit(1000000L, 0.01);
        // 添加数据
        bloomFilter.add("Shawn");
        // 判断是否存在
        System.out.println(bloomFilter.contains("xujunson"));
        System.out.println(bloomFilter.contains("Shawn"));
    }
}
