package com.atu.mysql.dto;

import com.atu.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-30 16:16
 * @description: 增量数据的投递
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {
    private String tableName;
    private String level;

    private OpType opType; //自定义的操作类型
    private List<Map<String, String>> fieldValueMap =new ArrayList<>();
}
