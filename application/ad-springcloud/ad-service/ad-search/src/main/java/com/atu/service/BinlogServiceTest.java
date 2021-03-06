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

//    Write---------------
//    WriteRowsEventData{tableId=85, includedColumns={0, 1, 2}, rows=[
//    [10, 10, 宝马]
//]}
//    Update--------------
//    UpdateRowsEventData{tableId=85, includedColumnsBeforeUpdate={0, 1, 2},
// includedColumns={0, 1, 2}, rows=[
//        {before=[10, 10, 宝马], after=[10, 11, 宝马]}
//]}
//    Delete--------------
//    DeleteRowsEventData{tableId=85, includedColumns={0, 1, 2}, rows=[
//    [11, 10, 奔驰]
//]}


//    WriteRowsEventData{tableId=109, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
//    [10, 15, 推广计划名称, 1, Wed Nov 28 08:00:00 CST 2018, Wed Nov 20 08:00:00 CST 2019, Tue Nov 20 04:42:27 CST 2018, Tue Nov 20 04:57:12 CST 2018]
//]}


    // Wed Nov 28 08:00:00 CST 2018

    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1",
                3306,
                "root",
                "111111");

        //client.setBinlogFilename();
        //client.getBinlogPosition();
        //注册事件监听器
        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update--------------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write---------------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete---------------");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }
}
