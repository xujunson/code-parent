package com.atu.mysql.dto;

import com.atu.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Tom
 * @date: 2020-03-30 11:48
 * @description: 为了将来操作时方便读取表的信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {
    private String tableName; //表名
    private String level; //层级

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 表达的是字段的索引到字段名的映射
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
