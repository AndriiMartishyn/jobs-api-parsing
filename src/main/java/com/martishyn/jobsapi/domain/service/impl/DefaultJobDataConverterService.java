package com.martishyn.jobsapi.domain.service.impl;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.response.JobDataResponse;
import com.martishyn.jobsapi.domain.service.JobDataConverterService;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class DefaultJobDataConverterService implements JobDataConverterService {
    @Override
    public JobDataDmo convertResponseDataToDmo(JobDataResponse jobDataResponse) {
        return JobDataDmo.builder()
                .slugName(jobDataResponse.getSlugName())
                .companyName(jobDataResponse.getCompanyName())
                .title(jobDataResponse.getTitle())
                .description(jobDataResponse.getDescription())
                .remote(jobDataResponse.getRemote().toString())
                .url(jobDataResponse.getUrl())
                .tags(jobDataResponse.getTags() == null ? null : String.join(", ", jobDataResponse.getTags()))
                .jobTypes(jobDataResponse.getJobTypes() == null ? null : String.join(", ", jobDataResponse.getJobTypes()))
                .location(jobDataResponse.getLocation())
                .createdAt(jobDataResponse.getCreatedAt())
                .build();
    }
}
