package com.martishyn.jobsapi.domain.controller;

import com.martishyn.jobsapi.domain.dto.*;
import com.martishyn.jobsapi.domain.service.server.JobsRestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/jobs")
@RequiredArgsConstructor
public class JobsApiController {

    private final JobsRestService jobsRestService;

    @GetMapping
    public ResponseEntity<ResponseDataWrapper<JobDataDto>> getAllJobsSortedAndOrdered(@RequestParam(defaultValue = "1") int currentPage,
                                                                         @RequestParam(defaultValue = "100") int pageSize,
                                                                         @RequestParam(value = "sortBy", defaultValue = "createdAt, desc") String[] sortingParams,
                                                                         HttpServletRequest request) {
        String field = sortingParams[0];
        String sortingDirection = sortingParams[1];
        Sort.Direction direction = sortingDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageReq = PageRequest.of(currentPage, pageSize, Sort.by(direction, field));
        return ResponseEntity.ok(ResponseDataWrapper.of(jobsRestService.findJobsPaginatedAndSorted(pageReq), currentPage, pageSize, request));
    }

    @GetMapping("/recent")
    public ResponseEntity<ResponseDataWrapper<JobDataDto>> getMostRecentJobsWith(@RequestParam(defaultValue = "10") int pageSize,
                                                                    HttpServletRequest request) {
        PageRequest pageRequest = PageRequest.ofSize(pageSize);
        return ResponseEntity.ok(ResponseDataWrapper.of(jobsRestService.findMostRecentJobs(pageRequest),1,  pageSize, request));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ResponseDataWrapper<LocationStatisticsDto>> getLocationsStatistics(HttpServletRequest request) {
        List<LocationStatisticsDto> statisticsByLocation = jobsRestService.findStatisticsByLocation();
        return ResponseEntity.ok(ResponseDataWrapper.of(statisticsByLocation, 1, statisticsByLocation.size(), request));
    }
}
