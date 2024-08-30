package com.martishyn.jobsapi.domain.client;

import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.JobsWrapperDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefaultArtbeitNowClient implements ArbeitNowClient {

    private static final String PAGE_PARAM = "?page=";

    private final RestTemplate restTemplate;

    @Value("${jobs.api.client.url}")
    private String ARBEIT_NOW_BASE_URL;

    @Override
    public List<JobDataDto> fetchJobForPage(int currentPage) {
        String url = ARBEIT_NOW_BASE_URL + PAGE_PARAM + currentPage;
        return Objects.requireNonNull(restTemplate.getForObject(url + currentPage, JobsWrapperDataDto.class)).getJobsData();
    }

    @Override
    public List<JobDataDto> fetchJobsUntilPage(int maxPage) {
        List<JobDataDto> fetchedJobsData = new ArrayList<>();
        for (int i = 1; i <= maxPage; i++) {
            List<JobDataDto> jobDataResponses = this.fetchJobForPage(i);
            fetchedJobsData.addAll(jobDataResponses);
        }
        return fetchedJobsData;
    }
}
