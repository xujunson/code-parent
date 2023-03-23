package com.atu.common.model;

import lombok.Data;

/**
 * @Author: Tom
 * @Date: 2022/6/23 11:27
 * @Description:
 */
@Data
public class SysOperLog {
    private Integer status;
    private String operIp;
    private String operUrl;
    private String operName;
    private String errorMsg;
    private String method;
    private String requestMethod;
    private Integer businessType;
    private String title;
    private Integer operatorType;
    private String jsonResult;
    private String operParam;

}
