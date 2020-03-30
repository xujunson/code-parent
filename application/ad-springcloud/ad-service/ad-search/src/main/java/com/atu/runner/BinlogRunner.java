package com.atu.runner;

import com.atu.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author: Tom
 * @date: 2020-03-30 17:45
 * @description: 实现当应用程序启动时，开启监听
 */
@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {

    @Autowired
    private BinlogClient client;

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Coming in BinlogRunner...");
        client.connect();
    }
}
