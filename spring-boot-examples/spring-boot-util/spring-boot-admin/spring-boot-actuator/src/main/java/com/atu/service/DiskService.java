package com.atu.service;

import com.atu.check.DiskInfo;
import com.atu.constant.WarningConstant;
import com.atu.utils.StringUtil;
import com.atu.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
@Slf4j
public class DiskService {
    /**
     * 检查每个磁盘使用情况
     *
     * @throws IOException
     */
    public static void checkUsageRate() throws IOException {

        Map<Double, String> usageRates = DiskInfo.getUseageRate();
        String ipAddress = SystemUtil.getIpAddress();
        for (Map.Entry<Double, String> entry : usageRates.entrySet()) {
            Double usageRate = entry.getKey();

            /**
             * 未达到预警范围
             */
            if (usageRate < WarningConstant.DISK_ALARM_THRESHOLD_1) {
                continue;
            }
            String content = entry.getValue() + "磁盘使用率:" + StringUtil.getPercent(usageRate);
            /**
             * 磁盘使用率过高，发送钉钉
             */
            if (usageRate > WarningConstant.DISK_ALARM_THRESHOLD_3) {
                log.info("服务器{}，{}", ipAddress, content);
                continue;
            }
        }
    }
}
