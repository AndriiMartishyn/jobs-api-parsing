package com.martishyn.jobsapi.domain.service.server.impl;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.service.converter.JobDataConverterService;
import com.martishyn.jobsapi.domain.service.server.JobsRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultJobsRestService implements JobsRestService {

    private final JobDataRepository jobDataRepository;

    private final JobDataConverterService jobDataConverterService;

    @Override
    public List<JobDataDto> findJobsPaginatedAndSorted(Pageable pageable) {
        Page<JobDataDmo> pageSlice = jobDataRepository.findAll(pageable);
        return pageSlice.getContent()
                .stream()
                .map(jobDataConverterService::convertJobDmoToJobDto)
                .toList();
    }

    @Override
    public List<JobDataDto> findMostRecentJobs(Pageable pageable) {
        return jobDataRepository.findMostRecentJobs(pageable)
                .stream()
                .map(jobDataConverterService::convertJobDmoToJobDto)
                .toList();
    }

    @Override
    public List<LocationStatisticsDto> findStatisticsByLocation() {
        return jobDataRepository.findLocationStatistics();
    }
}
