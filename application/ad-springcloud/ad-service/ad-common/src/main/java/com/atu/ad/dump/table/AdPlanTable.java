package com.atu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: Tom
 * @date: 2020-03-29 15:54
 * @description: 和索引对象属性相对应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {
    private Long id;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
}
