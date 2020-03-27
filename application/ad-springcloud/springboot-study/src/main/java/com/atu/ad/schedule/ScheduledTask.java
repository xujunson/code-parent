package com.atu.ad.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Scheduled 注解用于计划执行，它提供了多种参数控制执行的时间和频率等。
 * 例如：fixedRate 以固定的频率执行、cron 以 crontab 表达式的规则执行等等。
 * @Scheduled 任务是非常常用的，使用起来也非常简单：1. 加载到 Spring 容器中；2. 指定你想要的执行规则。
 */
@Component
public class ScheduledTask {

    // @Scheduled(fixedRate = 5000)         上一次开始执行时间点之后5秒再执行
    // @Scheduled(fixedDelay = 5000)        上一次执行完毕时间点之后5秒再执行
    // @Scheduled(cron = "*/5 * * * * *")   通过 crontab 表达式定义规则

    @Scheduled(fixedRate = 1000)
    public void helloSpringBoot() {
        System.out.println("Hello SpringBoot");
    }
}
