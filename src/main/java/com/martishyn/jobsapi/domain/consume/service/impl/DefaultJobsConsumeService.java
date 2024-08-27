package com.martishyn.jobsapi.domain.consume.service.impl;

import com.martishyn.jobsapi.domain.consume.service.JobsConsumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class DefaultJobsConsumeService implements JobsConsumeService {

    private static final String JOBS_API_URL = "https://www.arbeitnow.com/api/job-board-api";
    private final RestTemplate restTemplate;

    @Override
    public String fetchDataFromApi() {
        return restTemplate.getForObject(JOBS_API_URL, String.class);
    }
}
