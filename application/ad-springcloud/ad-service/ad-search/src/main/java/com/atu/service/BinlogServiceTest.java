package com.atu.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;

/**
 * @author: Tom
 * @date: 2020-03-30 11:15
 * @description: 测试服务
 * https://coding.imooc.com/lesson/310.html#mid=22016
 */
public class BinlogServiceTest {
    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1",
                3306,
                "root",
                "111111");

        //client.setBinlogFilename();
        //client.getBinlogPosition();
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if(data instanceof UpdateRowsEventData) {
                System.out.println("Update--------------");
                System.out.println(data.toString());
            } else if(data instanceof WriteRowsEventData) {
                System.out.println("Write---------------");
                System.out.println(data.toString());
            } else if(data instanceof DeleteRowsEventData) {
                System.out.println("Delete---------------");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }
}
