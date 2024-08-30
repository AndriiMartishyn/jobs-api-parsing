package com.martishyn.jobsapi.domain.service.client;

import com.martishyn.jobsapi.domain.client.ArbeitNowClient;
import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.service.client.impl.DefaultArbeitNowJsonProcessingService;
import com.martishyn.jobsapi.domain.service.converter.JobDataConverterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultArbeitNowJsonProcessingServiceTest {

    private static final long CREATED_AT_TIMESTAMP = 1L;

    @InjectMocks
    private static DefaultArbeitNowJsonProcessingService defaultArbeitNowJsonProcessingService;

    @Mock
    private ArbeitNowClient arbeitNowClient;

    @Mock
    private JobDataConverterService jobDataConverterService;

    @Mock
    private JobDataRepository jobDataRepository;

    private List<JobDataDto> jobDataDtoList;

    @Mock
    private List<JobDataDmo> jobDataDmoList;

    @Mock
    private JobDataDmo jobDataDmo;

    @BeforeEach
    public void setup() {
        JobDataDto fistJobDataDto = new JobDataDto();
        JobDataDto secondJobDataDto = new JobDataDto();
        fistJobDataDto.setCreatedAt(1L);
        secondJobDataDto.setCreatedAt(2L);
        jobDataDtoList = new ArrayList<>();
        jobDataDtoList.add(fistJobDataDto);
        jobDataDtoList.add(secondJobDataDto);
        ReflectionTestUtils.setField(defaultArbeitNowJsonProcessingService, "maxPageCount", 1);
    }

    @Test
    void shouldFetchDataAndSaveToDatabaseWhenInitMethodIsCalled() {
        when(arbeitNowClient.fetchJobsUntilPage(1)).thenReturn(jobDataDtoList);
        when(jobDataConverterService.convertJobDtoToJobDmoAndOrderByCreateDate(anyList())).thenReturn(jobDataDmoList);
        when(jobDataDmoList.getFirst()).thenReturn(jobDataDmo);
        when(jobDataDmo.getCreatedAt()).thenReturn(CREATED_AT_TIMESTAMP);
        when(jobDataRepository.saveAll(jobDataDmoList)).thenReturn(jobDataDmoList);

        defaultArbeitNowJsonProcessingService.processInitialDataFetch();

        Assertions.assertEquals(2, jobDataDtoList.size());
        verify(arbeitNowClient).fetchJobsUntilPage(1);
        verify(jobDataConverterService).convertJobDtoToJobDmoAndOrderByCreateDate(anyList());
        Assertions.assertEquals(CREATED_AT_TIMESTAMP, jobDataDmoList.getFirst().getCreatedAt());
        verify(jobDataRepository).saveAll(jobDataDmoList);
    }

    @Test
    void shouldNotSaveNewJobsWhenCreateTimeIsSameAsLastUpdateTime() {
        when(arbeitNowClient.fetchJobForPage(anyInt())).thenReturn(jobDataDtoList);
        ReflectionTestUtils.setField(defaultArbeitNowJsonProcessingService, "lastUpdateTimeInEpoch", 2L);

        defaultArbeitNowJsonProcessingService.processNewJobsData();

        verify(arbeitNowClient).fetchJobForPage(anyInt());
        verify(jobDataConverterService, never()).convertJobDtoToJobDmoAndOrderByCreateDate(any());
        verify(jobDataRepository, never()).saveAll(any());
    }

}
