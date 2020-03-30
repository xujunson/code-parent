package com.atu.mysql.listener;

import com.atu.mysql.dto.BinlogRowData;

/**
 * @author: Tom
 * @date: 2020-03-30 15:07
 * @description: 实现对binlog的多种实现
 */
public interface Ilistener {
    //注册不同监听器
    void register();

    //实现增量数据索引的更新
    void onEvent(BinlogRowData eventData);
}
