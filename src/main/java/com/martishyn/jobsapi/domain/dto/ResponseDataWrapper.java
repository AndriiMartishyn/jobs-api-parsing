package com.martishyn.jobsapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResponseDataWrapper<T>{

    private List<T> data;

    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("current_request_url")
    private String currentUrl;

    public ResponseDataWrapper(List<T> data, int currentPage, int perPage, String currentUrl) {
        this.data = data;
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.currentUrl = currentUrl;
    }

    public static <T> ResponseDataWrapper<T> of(List<T> jobsData, int currentPage, int perPage, HttpServletRequest request) {
        String collectedRequestParameters = extractParametersForUri(request);
        String requestUrl = request.getRequestURL().toString();
        String currentUrl  = collectedRequestParameters.isEmpty() ? requestUrl : requestUrl + "?" + collectedRequestParameters;
        return new ResponseDataWrapper<>(jobsData, currentPage, perPage, currentUrl);
    }

    private static String extractParametersForUri(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
                .collect(Collectors.joining("&"));
    }
}
