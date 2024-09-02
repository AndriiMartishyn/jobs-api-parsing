package com.martishyn.jobsapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class JobsWrapperDataDto {

    @JsonProperty(value = "data")
    List<JobDataDto> jobsData;

}
