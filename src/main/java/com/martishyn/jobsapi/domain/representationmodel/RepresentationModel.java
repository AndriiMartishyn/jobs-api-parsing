package com.martishyn.jobsapi.domain.representationmodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RepresentationModel {

    @JsonProperty("_links")
    private List<Link> links;

}
