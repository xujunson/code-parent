package com.atu.sender;

import com.atu.mysql.dto.MySqlRowData;

/**
 * @author: Tom
 * @date: 2020-03-30 16:33
 * @description: 投递增量数据的实现接口
 */
public interface ISender {
    void sender(MySqlRowData rowData);
}
