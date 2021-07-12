package com.atu.monitor.check;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
@Slf4j
public class CPUInfo {
    /**
     * cpu详情
     */
    public static CpuPerc[] cpu() {
        Sigar sigar = new Sigar();
        CpuPerc[] cpuList = null;
        try {
            cpuList = sigar.getCpuPercList();
        } catch (SigarException e) {
            log.error("error:", e);
        }
        return cpuList;
    }

}
