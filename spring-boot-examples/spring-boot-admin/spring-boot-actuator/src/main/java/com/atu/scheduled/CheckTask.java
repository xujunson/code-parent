package com.atu.scheduled;


import com.atu.service.CPUService;
import com.atu.service.DiskService;
import com.atu.service.DubboService;
import com.atu.service.MemoryService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: Tom
 * @Date: 2021/5/31 5:56 下午
 * @Description:
 */
@Component
@EnableScheduling
public class CheckTask {

    @Scheduled(fixedRate = 1000 * 6)
    public void check() throws Exception {

        CPUService.checkCPU();
        DiskService.checkUsageRate();
        MemoryService.checkUsageRate();
        DubboService.checkDubbo();
    }
}
