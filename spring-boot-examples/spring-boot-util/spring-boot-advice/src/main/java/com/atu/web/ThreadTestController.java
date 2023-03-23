package com.atu.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Tom
 * @Date: 2022/6/24 17:43
 * @Description:
 */
@Slf4j
@RestController
public class ThreadTestController {

    private static ExecutorService es = new ThreadPoolExecutor(50, 100, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(100000));


    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get() throws InterruptedException {
        for (int i = 0; i < 100000; i++) {
            es.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) es);

        while (true) {

            int corePoolSize = tpe.getCorePoolSize();
            log.info("corePoolSize：{}", corePoolSize);

            int maximumPoolSize = tpe.getMaximumPoolSize();
            log.info("maximumPoolSize：{}", maximumPoolSize);

            int queueSize = tpe.getQueue().size();
            log.info("当前排队线程数：{}", queueSize);

            int activeCount = tpe.getActiveCount();
            log.info("当前活动线程数：{}", activeCount);

            long completedTaskCount = tpe.getCompletedTaskCount();
            log.info("执行完成线程数：{}", completedTaskCount);

            long taskCount = tpe.getTaskCount();
            log.info("总线程数：{}", taskCount);

            Thread.sleep(3000);
        }
    }

    @RequestMapping(value = "/set", method = RequestMethod.POST)
    public void set(@RequestParam(value= "i") int i, @RequestParam(value= "j") int j) throws InterruptedException {

        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) es);
        tpe.setCorePoolSize(i);
        tpe.setMaximumPoolSize(j);
    }
}
