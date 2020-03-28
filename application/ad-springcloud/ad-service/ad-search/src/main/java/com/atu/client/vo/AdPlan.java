package com.atu.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: Tom
 * @date: 2020-03-28 17:49
 * @description: 响应对象
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlan {
    private Long id;
    private Long userId;
    private String planName;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
    private Date createTime;
    private Date updateTime;
}
