package com.martishyn.jobsapi.domain.client;

import com.martishyn.jobsapi.domain.response.JobDataResponse;
import com.martishyn.jobsapi.domain.response.JobsApiResponse;

import java.util.List;

public interface ArbeitNowClient {

    List<JobDataResponse> fetchApiData(int currentPage);
}
