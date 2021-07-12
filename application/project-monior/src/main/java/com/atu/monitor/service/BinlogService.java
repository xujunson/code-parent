package com.atu.monitor.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;

import java.io.IOException;

/**
 * @Author: Tom
 * @Date: 2021/7/12 4:53 下午
 * @Description:
 */
public class BinlogService {
    public static void main(String[] args) {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "123456");
        client.setServerId(2);

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof TableMapEventData) {
                System.out.println("Table:");
                TableMapEventData tableMapEventData = (TableMapEventData) data;
                System.out.println(tableMapEventData.getTableId()+": ["+tableMapEventData.getDatabase() + "-" + tableMapEventData.getTable()+"]");
            }
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update:");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Insert:");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete:");
                System.out.println(data.toString());
            }
        });

        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
