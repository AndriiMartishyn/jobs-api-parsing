package com.martishyn.jobsapi.domain.service;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;

import java.util.List;

public interface JobDataConverterService {

    JobDataDmo convertSingleResponseDataToDmo(JobDataDto jobDataResponse);

    List<JobDataDmo> convertResponseDataToDmoAndOrderByCreateDate(List<JobDataDto> jobDataResponses);
}
