package com.atu.design.pattern.chainfactory;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Tom
 * @Date: 2022/8/8 15:03
 * @Description:
 */
@Data
@AllArgsConstructor
public class GatewayEntity {

    private Integer handlerId;

    private String name;

    private String conference;

    private Integer preHandlerId;

    private Integer nextHandlerId;
}