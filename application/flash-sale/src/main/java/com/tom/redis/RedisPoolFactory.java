package com.tom.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: Tom
 * @date: 2020-02-27 16:25
 * @description:
 */
@Service
public class RedisPoolFactory {
    @Autowired
    RedisConfig redisConfig;

    /**
     * 加载jedisPool到spring容器
     *
     * @return
     */
    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        //毫秒
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool jp = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
        return jp;
    }
}
