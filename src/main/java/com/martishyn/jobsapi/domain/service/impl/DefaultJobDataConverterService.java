package com.martishyn.jobsapi.domain.service.impl;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.service.JobDataConverterService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultJobDataConverterService implements JobDataConverterService {

    @Override
    public JobDataDmo convertSingleResponseDataToDmo(JobDataDto jobDataResponse) {
        return JobDataDmo.builder()
                .slugName(jobDataResponse.getSlugName())
                .companyName(jobDataResponse.getCompanyName())
                .title(jobDataResponse.getTitle())
                .description(jobDataResponse.getDescription())
                .remote(jobDataResponse.getRemote().toString())
                .url(jobDataResponse.getUrl())
                .tags(jobDataResponse.getTags() == null ? "" : String.join(", ", jobDataResponse.getTags()))
                .jobTypes(jobDataResponse.getJobTypes() == null ? "" : String.join(", ", jobDataResponse.getJobTypes()))
                .location(jobDataResponse.getLocation())
                .createdAt(jobDataResponse.getCreatedAt())
                .build();
    }

    @Override
    public List<JobDataDmo> convertResponseDataToDmoAndOrderByCreateDate(List<JobDataDto> jobDataResponses) {
        return jobDataResponses.stream()
                .map(this::convertSingleResponseDataToDmo)
                .sorted(Comparator.comparingLong(JobDataDmo::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

}
