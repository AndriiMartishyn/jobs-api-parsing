package com.martishyn.jobsapi.domain.service.server;

import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobsRestService {

    List<JobDataDto> findJobsPaginatedAndSorted(Pageable pageable);

    List<JobDataDto> findMostRecentJobs(Pageable pageable);

    List<LocationStatisticsDto> findStatisticsByLocation();
}
