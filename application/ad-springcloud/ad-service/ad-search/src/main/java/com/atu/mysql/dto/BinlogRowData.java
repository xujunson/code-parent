package com.atu.mysql.dto;


import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-30 14:59
 * @description: 基于Event——实现将Binlog解析成java对象
 */
@Data
public class BinlogRowData {

    //把表包含的所有信息包含进来
    private TableTemplate table;

    //操作类型
    private EventType eventType;

    //更新之后的值
    private List<Map<String, String>> after;
    private List<Map<String, String>> before;


}
