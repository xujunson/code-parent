package com.atu.task;

import com.atu.business.CardHangUpManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledService {
    @Autowired
    private CardHangUpManager cardHangUpManagerImpl;

    @Scheduled(cron = "${job.cron}")
    public void scheduled() {
        log.info("============开始进行挂起操作============");
        cardHangUpManagerImpl.hangUpCard();
        log.info("============结束挂起操作============");
    }
}
