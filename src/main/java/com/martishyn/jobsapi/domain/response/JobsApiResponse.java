package com.martishyn.jobsapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class JobsApiResponse {
    @JsonProperty(value = "data")
    List<JobDataResponse> jobsData = new ArrayList<>();
}
