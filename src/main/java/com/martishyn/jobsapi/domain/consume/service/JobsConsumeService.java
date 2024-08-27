package com.martishyn.jobsapi.domain.consume.service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface JobsConsumeService {

    List<JsonNode> fetchDataFromApi();

}
