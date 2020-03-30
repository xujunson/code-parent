package com.atu.mysql;

import com.atu.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author: Tom
 * @date: 2020-03-30 17:24
 * @description:
 */
@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;
    @Autowired
    private BinlogConfig config;

    @Autowired
    private AggregationListener listener;

    //程序启动时启动监听
    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(listener);
            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    //断开监听
    public void close() {
        try {
            client.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
