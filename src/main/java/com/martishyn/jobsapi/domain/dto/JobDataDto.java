package com.martishyn.jobsapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.martishyn.jobsapi.domain.representationmodel.RepresentationModel;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"slugName", "companyName", "title", "description", "remote", "url", "tags", "jobTypes", "location", "createdAt", "_links"})
public class JobDataDto extends RepresentationModel {

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
