package com.martishyn.jobsapi.domain.controller;

import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import com.martishyn.jobsapi.domain.service.server.JobsRestService;
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
    public ResponseEntity<List<JobDataDto>> getAllJobsSortedAndOrdered(@RequestParam(defaultValue = "1") int currentPage,
                                                                       @RequestParam(defaultValue = "100") int pageSize,
                                                                       @RequestParam(value = "sortBy", defaultValue = "createdAt, desc") String[] sortingParams) {
        String field = sortingParams[0];
        String sortingDirection = sortingParams[1];
        Sort.Direction direction = sortingDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageReq = PageRequest.of(currentPage, pageSize, Sort.by(direction, field));
        List<JobDataDto> jobsPage = jobsRestService.findJobsPaginatedAndSorted(pageReq);
        return ResponseEntity.ok(jobsPage);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<JobDataDto>> getMostRecentJobsWith(@RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.ofSize(size);
        return ResponseEntity.ok(jobsRestService.findMostRecentJobs(pageRequest));
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<LocationStatisticsDto>> getLocationsStatistics() {
        return ResponseEntity.ok(jobsRestService.findStatisticsByLocation());
    }
}
