package com.martishyn.jobsapi.domain.service.converter;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;

import java.util.List;

public interface JobDataConverterService {

    JobDataDmo convertJobDtoToJobDmo(JobDataDto jobDataDto);

    List<JobDataDmo> convertJobDtoToJobDmoAndOrderByCreateDate(List<JobDataDto> jobDataDtoList);

    JobDataDto convertJobDmoToJobDto(JobDataDmo JobDataDmo);
}
