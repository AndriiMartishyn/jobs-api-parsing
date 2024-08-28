package com.martishyn.jobsapi;

import com.martishyn.jobsapi.domain.client.ArbeitNowClient;
import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.repository.JobDataRepository;
import com.martishyn.jobsapi.domain.service.JobDataConverterService;
import com.martishyn.jobsapi.domain.service.impl.DefaultArbeitNowJsonProcessingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

    @Spy
    private static List<JobDataDto> jobDataResponseList;

    @Mock
    private List<JobDataDmo> jobDataDmoList;

    @Mock
    private JobDataDmo jobDataDmo;

    @BeforeAll
    public static void setup() {
        JobDataDto firstJobDataResponse = new JobDataDto();
        JobDataDto secondJobDataResponse = new JobDataDto();
        firstJobDataResponse.setCreatedAt(1L);
        secondJobDataResponse.setCreatedAt(2L);
        jobDataResponseList = new ArrayList<>();
        jobDataResponseList.add(firstJobDataResponse);
        jobDataResponseList.add(secondJobDataResponse);

    }

    @Test
    void shouldFetchDataAndSaveToDatabaseWhenInitMethodIsCalled() {
        when(arbeitNowClient.fetchJobForPage(anyInt())).thenReturn(jobDataResponseList);
        when(jobDataConverterService.convertResponseDataToDmoAndOrderByCreateDate(any(List.class))).thenReturn(jobDataDmoList);
        when(jobDataDmoList.getFirst()).thenReturn(jobDataDmo);
        when(jobDataDmo.getCreatedAt()).thenReturn(CREATED_AT_TIMESTAMP);
        when(jobDataRepository.saveAll(jobDataDmoList)).thenReturn(jobDataDmoList);
        ReflectionTestUtils.setField(defaultArbeitNowJsonProcessingService, "maxPageCount", 1);

        defaultArbeitNowJsonProcessingService.processInitialDataFetch();

        Assertions.assertEquals(2, jobDataResponseList.size());
        verify(arbeitNowClient).fetchJobForPage(anyInt());
        verify(jobDataConverterService).convertResponseDataToDmoAndOrderByCreateDate(any(List.class));
        Assertions.assertEquals(CREATED_AT_TIMESTAMP, jobDataDmoList.getFirst().getCreatedAt());
        verify(jobDataRepository).saveAll(jobDataDmoList);
    }

    @Test
    void shouldNotSaveNewJobsWhenCreateTimeIsSameAsLastUpdateTime() {
        when(arbeitNowClient.fetchJobForPage(anyInt())).thenReturn(jobDataResponseList);
        ReflectionTestUtils.setField(defaultArbeitNowJsonProcessingService, "lastUpdateTimeInEpoch", 2L);
        ReflectionTestUtils.setField(defaultArbeitNowJsonProcessingService, "maxPageCount", 1);

        defaultArbeitNowJsonProcessingService.processNewJobsData();

        verify(arbeitNowClient).fetchJobForPage(anyInt());
        verify(jobDataConverterService, never()).convertResponseDataToDmoAndOrderByCreateDate(any());
        verify(jobDataRepository, never()).saveAll(any());
    }

}
