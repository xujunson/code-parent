package com.atu.service;


import com.atu.check.CPUInfo;
import com.atu.constant.WarningConstant;
import com.atu.utils.StringUtil;
import com.atu.utils.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
@Slf4j
@Service
public class CPUService {

    public static void checkCPU() throws IOException {
        try {
            CPUInfo.cpu();
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }
}
