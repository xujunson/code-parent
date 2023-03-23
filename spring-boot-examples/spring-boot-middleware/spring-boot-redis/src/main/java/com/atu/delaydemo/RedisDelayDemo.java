package com.atu.delaydemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @Author: Tom
 * @Date: 2021/10/24 11:53
 * @Description:
 */
@Configuration
public class RedisDelayDemo {
    @Autowired
    private RedisTemplate redisTemplate;

    public void setDelayTasks(long delayTime) {
        String orderId = UUID.randomUUID().toString();
        Boolean addResult = redisTemplate.opsForZSet().add("delayQueue", orderId, System.currentTimeMillis() + delayTime);
        if (addResult) {
            System.out.println("添加任务成功！" + orderId + ", 当前时间为" + LocalDateTime.now());
        }
    }

    /**
     * 监听延迟消息
     */
    public void listenDelayLoop() {
        while (true) {
            // 获取一个到点的消息
            Set<String> set = redisTemplate.opsForZSet().rangeByScore("delayQueue", 0, System.currentTimeMillis(), 0, 1);

            // 如果没有，就等等
            if (set.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 继续执行
                continue;
            }
            // 获取具体消息的key
            String it = set.iterator().next();
            // 删除成功
            if (redisTemplate.opsForZSet().remove("delayQueue", it) > 0) {
                // 拿到任务
                System.out.println("消息到期" + it + ",时间为" + LocalDateTime.now());
            }
        }
    }
}
