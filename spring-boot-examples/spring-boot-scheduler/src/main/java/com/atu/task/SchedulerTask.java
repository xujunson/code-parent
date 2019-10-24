package com.atu.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ：mark
 * @date ：Created in 2019/10/24 11:56
 * @description：
 */
@Component
public class SchedulerTask {
    private int count=0;

    @Scheduled(cron="*/1 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }

}
