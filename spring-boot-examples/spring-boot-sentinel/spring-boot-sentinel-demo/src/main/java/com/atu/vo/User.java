package com.atu.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Tom
 * @Date: 2021/7/29 10:55 上午
 * @Description:
 */
@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String desc;
    private Integer code;
}
