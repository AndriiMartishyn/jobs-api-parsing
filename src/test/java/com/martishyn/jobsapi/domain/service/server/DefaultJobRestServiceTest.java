package com.martishyn.jobsapi.domain.service.server;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.service.converter.JobDataConverterService;
import com.martishyn.jobsapi.domain.service.server.impl.DefaultJobsRestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultJobRestServiceTest {

    @InjectMocks
    private DefaultJobsRestService defaultJobsRestService;

    @Mock
    private Pageable pageable;

    @Mock
    private JobDataRepository jobDataRepository;

    @Mock
    private JobDataConverterService jobDataConverterService;

    @Mock
    private JobDataDmo jobDataDmo;

    @Mock
    private JobDataDto jobDataDto;

    @Mock
    private LocationStatisticsDto locationStatisticsDto;

    @DisplayName("find-paginated-and-sorted-returning-dto")
    @Test
    void shouldReturnJobDtoConvertedFromDatabaseCallWhenFindingJobsPaginatedAndSorted(){
        Page<JobDataDmo> page = new PageImpl<>(List.of(jobDataDmo));
        when(jobDataRepository.findAll(pageable)).thenReturn(page);
        when(jobDataConverterService.convertJobDmoToJobDto(jobDataDmo)).thenReturn(jobDataDto);

        List<JobDataDto> actualJobsPaginatedAndSorted = defaultJobsRestService.findJobsPaginatedAndSorted(pageable);

        Assertions.assertNotNull(actualJobsPaginatedAndSorted);
        Assertions.assertEquals(jobDataDto, actualJobsPaginatedAndSorted.getFirst());
        Assertions.assertEquals(List.of(jobDataDto), actualJobsPaginatedAndSorted);
        verify(jobDataRepository).findAll(pageable);
        verify(jobDataConverterService).convertJobDmoToJobDto(jobDataDmo);
    }

    @DisplayName("find-most-recent-returning-dto")
    @Test
    void shouldReturnJobDtoConvertedFromDatabaseCallWhenFindingMostRecentJobs(){
        when(jobDataRepository.findMostRecentJobs(pageable)).thenReturn(Collections.singletonList(jobDataDmo));
        when(jobDataConverterService.convertJobDmoToJobDto(jobDataDmo)).thenReturn(jobDataDto);

        List<JobDataDto> actualMostRecentJobs = defaultJobsRestService.findMostRecentJobs(pageable);

        Assertions.assertNotNull(actualMostRecentJobs);
        Assertions.assertEquals(jobDataDto, actualMostRecentJobs.getFirst());
        Assertions.assertEquals(List.of(jobDataDto), actualMostRecentJobs);
        verify(jobDataRepository).findMostRecentJobs(pageable);
        verify(jobDataConverterService).convertJobDmoToJobDto(jobDataDmo);
    }

    @DisplayName("find-location-statistics-returning-dto")
    @Test
    void shouldReturnLocationStatisticsFromDatabaseCallWhenFindingLocationStatistics() {
        when(jobDataRepository.findLocationStatistics()).thenReturn(Collections.singletonList(locationStatisticsDto));

        List<LocationStatisticsDto> actualStatisticsByLocation = defaultJobsRestService.findStatisticsByLocation();

        Assertions.assertNotNull(actualStatisticsByLocation);
        Assertions.assertEquals(Collections.singletonList(locationStatisticsDto), actualStatisticsByLocation);
    }
}
