package com.atu.design.pattern.chainfactory;

/**
 * @Author: Tom
 * @Date: 2022/8/8 15:06
 * @Description:
 */
public interface GatewayDao {

    /**
     * 根据 handlerId 获取配置项
     * @param handlerId
     * @return
     */
    GatewayEntity getGatewayEntity(Integer handlerId);

    /**
     * 获取第一个处理者
     * @return
     */
    GatewayEntity getFirstGatewayEntity();
}