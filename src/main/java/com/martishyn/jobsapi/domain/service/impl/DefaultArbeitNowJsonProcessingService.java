package com.martishyn.jobsapi.domain.service.impl;

import com.martishyn.jobsapi.domain.client.ArbeitNowClient;
import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.response.JobDataResponse;
import com.martishyn.jobsapi.domain.service.ArbeitNowJsonProcessingService;
import com.martishyn.jobsapi.domain.service.JobDataConverterService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
        processInitialDataFetch();
    }

    @Override
    public void processInitialDataFetch() {
        List<JobDataResponse> fetchedJobsData = new ArrayList<>();
        for (int i = 1; i <= maxPageCount; i++) {
            List<JobDataResponse> jobDataResponses = arbeitNowClient.fetchApiData(i);
            fetchedJobsData.addAll(jobDataResponses);
        }
        List<JobDataDmo> jobDataDmos = jobDataConverterService.convertResponseDataToDmoAndOrderByCreateDate(fetchedJobsData);
        lastUpdateTimeInEpoch = jobDataDmos.getFirst().getCreatedAt();
        jobDataRepository.saveAll(jobDataDmos);
    }

    @Override
    public void processNewJobsData() {
        List<JobDataResponse> updatedJobDataResponse = new ArrayList<>();
        int pageCounter = 1;
        boolean hasMorePage = true;
        while (hasMorePage) {
            List<JobDataResponse> jobDataResponses = arbeitNowClient.fetchApiData(pageCounter);
            List<JobDataResponse> updatedJobData = jobDataResponses.stream()
                    .filter(job -> job.getCreatedAt() > lastUpdateTimeInEpoch)
                    .toList();
            updatedJobDataResponse.addAll(0, updatedJobData);
            if (!updatedJobData.isEmpty()) {
                lastUpdateTimeInEpoch = updatedJobData.getFirst().getCreatedAt();
                List<JobDataDmo> updatedJobDataDmo = jobDataConverterService.convertResponseDataToDmoAndOrderByCreateDate(updatedJobDataResponse);
                jobDataRepository.saveAll( updatedJobDataDmo);
                pageCounter++;
            } else {
                hasMorePage = false;
            }
        }
    }
}
