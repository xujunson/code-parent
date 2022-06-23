package com.atu.common.factory;

import com.atu.common.model.SysOperLog;
import com.atu.common.utils.SpringUtils;
import com.atu.service.ISysOperLogService;

import java.util.TimerTask;

/**
 * @Author: Tom
 * @Date: 2022/6/23 15:09
 * @Description:
 */
public class AsyncFactory {
    public static TimerTask recordOper(final SysOperLog sysOperLog) {
        return new TimerTask() {
            @Override
            public void run() {
                SpringUtils.getBean(ISysOperLogService.class).insertOperLog(sysOperLog);
            }
        };
    }
}
