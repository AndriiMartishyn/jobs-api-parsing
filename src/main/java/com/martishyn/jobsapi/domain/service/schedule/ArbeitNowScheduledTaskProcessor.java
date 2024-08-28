package com.martishyn.jobsapi.domain.service.schedule;

import com.martishyn.jobsapi.domain.service.ArbeitNowJsonProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RequiredArgsConstructor
public class ArbeitNowScheduledTaskProcessor {

    private final ArbeitNowJsonProcessingService arbeitNowJsonProcessingService;

    @Scheduled(cron = "0 50 */1 * * *")  // Every 50 minutes
    public void fetchNewData() {
        arbeitNowJsonProcessingService.processNewJobsData();
    }
}
