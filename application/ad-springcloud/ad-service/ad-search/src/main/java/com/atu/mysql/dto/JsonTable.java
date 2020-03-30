package com.atu.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-30 11:43
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {
    //表名
    private String tableName;

    //层级
    private Integer level;

    private List<Column> insert;
    private List<Column> update;
    private List<Column> delete;

    //内部类
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {
        private String column;
    }
}
