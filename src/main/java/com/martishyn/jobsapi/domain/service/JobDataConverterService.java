package com.martishyn.jobsapi.domain.service;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.response.JobDataResponse;

import java.util.List;

public interface JobDataConverterService {

    JobDataDmo convertSingleResponseDataToDmo(JobDataResponse jobDataResponse);

    List<JobDataDmo> convertResponseDataToDmoAndOrderByCreateDate(List<JobDataResponse> jobDataResponses);
}
