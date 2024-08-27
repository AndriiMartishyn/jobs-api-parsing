package com.martishyn.jobsapi.domain.consume.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.martishyn.jobsapi.domain.consume.service.JobsConsumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultJobsConsumeService implements JobsConsumeService {

    private static final String JOBS_API_URL = "https://www.arbeitnow.com/api/job-board-api?page=";
    private final RestTemplate restTemplate;
    @Value("${jobs.api.consumer.page.number}")
    private int pageNumber;
    private List<JsonNode> jobs = new ArrayList<>();

    @Override
    public List<JsonNode> fetchDataFromApi() {
        for (int i = 1; i <= pageNumber; i++) {
            jobs.add(restTemplate.getForObject(JOBS_API_URL + i, JsonNode.class));
        }
        return jobs;
    }
}
