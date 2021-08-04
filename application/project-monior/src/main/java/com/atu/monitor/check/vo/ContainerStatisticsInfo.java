package com.atu.monitor.check.vo;

import com.github.dockerjava.api.model.Statistics;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: Tom
 * @Date: 2021/8/4 23:49
 * @Description:
 */
@Data
@Builder
public class ContainerStatisticsInfo {
    Statistics statistics;
    boolean ok;
}
