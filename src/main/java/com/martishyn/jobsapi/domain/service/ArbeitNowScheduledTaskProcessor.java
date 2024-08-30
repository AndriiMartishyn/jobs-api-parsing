package com.martishyn.jobsapi.domain.service;

import com.martishyn.jobsapi.domain.service.client.impl.ArbeitNowJsonProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArbeitNowScheduledTaskProcessor {

    private final ArbeitNowJsonProcessingService arbeitNowJsonProcessingService;

    @Scheduled(cron = "${jobs.api.schedule.cronjob.rate}")
    public void fetchNewData() {
        arbeitNowJsonProcessingService.processNewJobsData();
    }
}
