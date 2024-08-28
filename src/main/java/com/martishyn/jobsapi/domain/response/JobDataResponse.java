package com.martishyn.jobsapi.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobDataResponse {
    @JsonProperty("slug")
    private String slugName;
    @JsonProperty("company_name")
    private String companyName;
    private String title;
    private String description;
    @JsonProperty
    private Boolean remote;
    private String url;
    private List<String> tags;
    private List<String> jobTypes;
    private String location;
    @JsonProperty("created_at")
    private long createdAt;
}
