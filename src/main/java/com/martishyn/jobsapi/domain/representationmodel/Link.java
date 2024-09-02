package com.martishyn.jobsapi.domain.representationmodel;

import lombok.Data;

@Data
public class Link {

    private String self;

    public Link(String url) {
        this.self = url;
    }

}
