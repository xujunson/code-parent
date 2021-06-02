package com.atu.service;

import com.atu.check.MemoryInfo;
import com.atu.constant.WarningConstant;
import com.atu.utils.StringUtil;
import com.atu.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.SigarException;

import java.io.IOException;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
@Slf4j
public class MemoryService {
    public static void checkUsageRate() throws IOException {

        double usageRate = MemoryInfo.getUsageRate();
        try {
            MemoryInfo.memory();
        } catch (SigarException e) {
            e.printStackTrace();
        }

        /**
         * 未达到预警范围
         */
        if (usageRate < WarningConstant.MEMORY_ALARM_THRESHOLD_1) {
            return;
        }
        String content = "内存使用率:" + StringUtil.getPercent(usageRate);
        String ipAddress = SystemUtil.getIpAddress();
        /**
         * 内存使用率过高 发送钉钉
         */
        if (usageRate > WarningConstant.MEMORY_ALARM_THRESHOLD_3) {
            log.error("服务器{}，{} ", ipAddress, content);
            return;
        }
    }
}
