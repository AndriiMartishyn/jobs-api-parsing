package com.martishyn.jobsapi.domain.service;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.response.JobDataResponse;

public interface JobDataConverterService {

    JobDataDmo convertResponseDataToDmo(JobDataResponse jobDataResponse);
}
