package com.martishyn.jobsapi.domain.service.client.impl;

import com.martishyn.jobsapi.domain.client.ArbeitNowClient;
import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.service.converter.JobDataConverterService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
        List<JobDataDto> jobs = arbeitNowClient.fetchJobsUntilPage(maxPageCount);
        List<JobDataDmo> jobDataDmos = jobDataConverterService.convertJobDtoToJobDmoAndOrderByCreateDate(jobs);
        lastUpdateTimeInEpoch = jobDataDmos.getFirst().getCreatedAt();
        jobDataRepository.saveAll(jobDataDmos);
    }

    @Override
    public void processNewJobsData() {
        boolean hasNewJobs = true;
        int pageCounter = 1;
        List<JobDataDto> updatedJobDataResponse = new LinkedList<>();
        while (hasNewJobs) {
            List<JobDataDto> updatedJobData = arbeitNowClient.fetchJobForPage(pageCounter)
                    .stream()
                    .filter(job -> job.getCreatedAt() > lastUpdateTimeInEpoch)
                    .toList();
            updatedJobDataResponse.addAll(0, updatedJobData);
            if (!updatedJobData.isEmpty()) {
                lastUpdateTimeInEpoch = updatedJobData.getFirst().getCreatedAt();
                List<JobDataDmo> updatedJobDataDmo = jobDataConverterService.convertJobDtoToJobDmoAndOrderByCreateDate(updatedJobDataResponse);
                jobDataRepository.saveAll(updatedJobDataDmo);
                pageCounter++;
            } else {
                hasNewJobs = false;
            }
        }
    }
}
