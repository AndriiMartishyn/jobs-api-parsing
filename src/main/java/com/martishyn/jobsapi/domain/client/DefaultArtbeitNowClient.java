package com.martishyn.jobsapi.domain.client;

import com.martishyn.jobsapi.domain.response.JobDataResponse;
import com.martishyn.jobsapi.domain.response.JobsApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultArtbeitNowClient implements ArbeitNowClient {

    private final RestTemplate restTemplate;
    @Value("${jobs.api.client.url}")
    private String ARBEIT_NOW_URL;

    @Override
    public List<JobDataResponse> fetchApiData(int currentPage) {
         return restTemplate.getForObject(ARBEIT_NOW_URL + currentPage, JobsApiResponse.class).getJobsData();
    }
}
