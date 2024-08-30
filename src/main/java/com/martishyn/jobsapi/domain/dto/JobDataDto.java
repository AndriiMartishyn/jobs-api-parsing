package com.martishyn.jobsapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDataDto {

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
