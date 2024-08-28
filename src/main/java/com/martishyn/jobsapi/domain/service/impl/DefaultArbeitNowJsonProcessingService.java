package com.martishyn.jobsapi.domain.service.impl;

import com.martishyn.jobsapi.domain.client.ArbeitNowClient;
import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.response.JobDataResponse;
import com.martishyn.jobsapi.domain.response.JobsApiResponse;
import com.martishyn.jobsapi.domain.service.ArbeitNowJsonProcessingService;
import com.martishyn.jobsapi.domain.service.JobDataConverterService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultArbeitNowJsonProcessingService implements ArbeitNowJsonProcessingService {

    private final ArbeitNowClient arbeitNowClient;
    private final JobDataConverterService jobDataConverterService;
    private final JobDataRepository jobDataRepository;
    @Value("${jobs.api.client.max.page.count}")
    private int maxPageCount;
    private long lastUpdateTimeInEpoch;

    @PostConstruct
    public void init() {
        List<JobDataResponse> jobs = new ArrayList<>();
        for (int i = 1; i <= maxPageCount; i++) {
            List<JobDataResponse> jobDataResponses = arbeitNowClient.fetchApiData(i);
            jobs.addAll(jobDataResponses);
        }
        List<JobDataDmo> jobDmos = jobs.stream()
                .map(jobDataConverterService::convertResponseDataToDmo)
                .sorted(Comparator.comparingLong(JobDataDmo::getCreatedAt).reversed())
                .collect(Collectors.toList());
        lastUpdateTimeInEpoch = jobDmos.getFirst().getCreatedAt();
        jobDataRepository.saveAll(jobDmos);
    }

    @Override
    public void processNewJobsData() {
        int pageCounter = 1;
        boolean hasMorePage = true;
        while (hasMorePage) {
            List<JobDataResponse> jobDataResponses = arbeitNowClient.fetchApiData(pageCounter);
            List<JobDataResponse> updatedJobData = jobDataResponses.stream()
                    .filter(job -> job.getCreatedAt() > lastUpdateTimeInEpoch)
                    .toList();
            if (!updatedJobData.isEmpty()) {
                lastUpdateTimeInEpoch = updatedJobData.getFirst().getCreatedAt();
                pageCounter++;
            } else {
                hasMorePage = false;
            }
        }
    }
}
