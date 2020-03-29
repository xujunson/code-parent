package com.atu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tom
 * @date: 2020-03-29 15:55
 * @description: 将来可以使用表字段的定义去导出对应的数据文件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItTable {

    private Long unitId;
    private String itTag;
}