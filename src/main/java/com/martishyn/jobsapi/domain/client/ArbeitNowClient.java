package com.martishyn.jobsapi.domain.client;

import com.martishyn.jobsapi.domain.dto.JobDataDto;

import java.util.List;

public interface ArbeitNowClient {

    List<JobDataDto> fetchJobForPage(int currentPage);

    List<JobDataDto> fetchJobsUntilPage(int maxPage);

}
