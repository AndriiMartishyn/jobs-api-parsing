package com.martishyn.jobsapi.domain.service.converter.impl;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.service.converter.JobDataConverterService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultJobDataConverterService implements JobDataConverterService {

    @Override
    public JobDataDmo convertJobDtoToJobDmo(JobDataDto jobDataDto) {
        return JobDataDmo.builder()
                .slugName(jobDataDto.getSlugName())
                .companyName(jobDataDto.getCompanyName())
                .title(jobDataDto.getTitle())
                .description(jobDataDto.getDescription())
                .remote(jobDataDto.getRemote().toString())
                .url(jobDataDto.getUrl())
                .tags(jobDataDto.getTags() == null ? "" : String.join(", ", jobDataDto.getTags()))
                .jobTypes(jobDataDto.getJobTypes() == null ? "" : String.join(", ", jobDataDto.getJobTypes()))
                .location(jobDataDto.getLocation())
                .createdAt(jobDataDto.getCreatedAt())
                .build();
    }

    @Override
    public List<JobDataDmo> convertJobDtoToJobDmoAndOrderByCreateDate(List<JobDataDto> jobDataDtoList) {
        return jobDataDtoList.stream()
                .map(this::convertJobDtoToJobDmo)
                .sorted(Comparator.comparingLong(JobDataDmo::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public JobDataDto convertJobDmoToJobDto(JobDataDmo jobDataDmo) {
        return JobDataDto.builder()
                .slugName(jobDataDmo.getSlugName())
                .companyName(jobDataDmo.getCompanyName())
                .title(jobDataDmo.getTitle())
                .description(jobDataDmo.getDescription())
                .remote(Boolean.valueOf(jobDataDmo.getRemote()))
                .url(jobDataDmo.getUrl())
                .tags(Collections.singletonList(jobDataDmo.getTags()))
                .jobTypes(Collections.singletonList(jobDataDmo.getJobTypes()))
                .location(jobDataDmo.getLocation())
                .createdAt(jobDataDmo.getCreatedAt())
                .build();
    }

}
