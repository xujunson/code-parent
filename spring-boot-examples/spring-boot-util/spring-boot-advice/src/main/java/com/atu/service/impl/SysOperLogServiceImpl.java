package com.atu.service.impl;

import com.alibaba.fastjson.JSON;
import com.atu.common.model.SysOperLog;
import com.atu.service.ISysOperLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Tom
 * @Date: 2022/6/23 15:14
 * @Description:
 */
@Slf4j
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {
    @Override
    public int insertOperLog(SysOperLog sysOperLog) {
        log.info("insertOperLog::sysOperLog:{}", JSON.toJSON(sysOperLog));
        return 0;
    }
}
