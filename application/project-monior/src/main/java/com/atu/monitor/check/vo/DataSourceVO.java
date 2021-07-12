package com.atu.monitor.check.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: Tom
 * @Date: 2021/7/1 10:36 上午
 * @Description:
 */
@Data
public class DataSourceVO {
    private Integer resultCode;
    private List<DruidDataSourceStatValueVO> content;
}
