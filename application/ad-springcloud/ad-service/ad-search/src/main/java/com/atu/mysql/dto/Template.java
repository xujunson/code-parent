package com.atu.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: Tom
 * @date: 2020-03-30 11:46
 * @description: 对template.json模板文件中的表做一个表达
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {
    private String database;
    private List<JsonTable> tableList;
}
