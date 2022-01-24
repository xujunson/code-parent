package com.atu.realtimelog.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

    private static Logger logger = LoggerFactory.getLogger(TestTask.class);

    private static int num = 1;


    @Scheduled(cron = "*/1 * * * * ?")
    public void test3() {
        logger.info("模拟日志：num = " + num);
        int a = 20 / num;
        num++;
    }

}
