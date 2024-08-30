package com.martishyn.jobsapi.domain.controller;

import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import com.martishyn.jobsapi.domain.service.server.JobsRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<JobDataDto>> getAllJobs(@RequestParam(defaultValue = "1") int currentPage,
                                                       @RequestParam(defaultValue = "100") int size,
                                                       @RequestParam(defaultValue = "id") String sortedBy) {
        Pageable pageReq = PageRequest.of(currentPage, size, Sort.by(Sort.Direction.DESC, sortedBy));
        List<JobDataDto> jobsPage = jobsRestService.findJobsPaginatedAndSorted(pageReq);
        return ResponseEntity.ok(jobsPage);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<JobDataDto>> getMostRecentJobsWith(@RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return ResponseEntity.ok(jobsRestService.findMostRecentJobs(pageRequest));
    }

    @GetMapping
    public ResponseEntity<List<LocationStatisticsDto>> getLocationsStatistics() {
        return ResponseEntity.ok(jobsRestService.findStatisticsByLocation());
    }
}
