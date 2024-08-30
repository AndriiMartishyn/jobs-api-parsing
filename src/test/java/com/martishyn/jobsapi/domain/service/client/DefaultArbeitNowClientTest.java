package com.martishyn.jobsapi.domain.service.client;

import com.martishyn.jobsapi.domain.client.DefaultArtbeitNowClient;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.JobsWrapperDataDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultArbeitNowClientTest {

    private static final String BASE_API_URL = "https://www.arbeitnow.com/api/job-board-api";

    private static final String PAGE_PARAM = "?page=";

    private static final int FIRST_PAGE_NUMBER = 1;

    private static final int SECOND_PAGE_NUMBER = 2;

    @InjectMocks
    private DefaultArtbeitNowClient defaultArbeitNowClient;

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private JobsWrapperDataDto jobsWrapperDataDto;

    private List<JobDataDto> jobDataDtoList;

    @BeforeEach
    void setUp() {
        jobDataDtoList = new ArrayList<>();
        ReflectionTestUtils.setField(defaultArbeitNowClient, "ARBEIT_NOW_BASE_URL", BASE_API_URL);
        when(restTemplate.getForObject(BASE_API_URL + PAGE_PARAM + FIRST_PAGE_NUMBER, JobsWrapperDataDto.class)).thenReturn(jobsWrapperDataDto);
        jobDataDtoList.add(JobDataDto.builder().title("test-slug1").build());
        jobDataDtoList.add(JobDataDto.builder().title("test-slug2").build());
        when(jobsWrapperDataDto.getJobsData()).thenReturn(jobDataDtoList);
    }

    @DisplayName("fetch-jobs-for-page-with-current-page-param")
    @Test
    void shouldFetchJobForPageWithRestTemplateWhenCallingWIthValidCurrentPageParam() {
        List<JobDataDto> jobDataDtos = defaultArbeitNowClient.fetchJobForPage(FIRST_PAGE_NUMBER);

        Assertions.assertNotNull(jobDataDtos);
        verify(restTemplate).getForObject(BASE_API_URL + PAGE_PARAM + FIRST_PAGE_NUMBER, JobsWrapperDataDto.class);
    }

    @DisplayName("fetch-jobs-until-page-with-max-page-param")
    @Test
    void shouldFetchJobsUntilCertainPageWithRestTemplateWhenCallingWIthValidMaxPageParam() {
        when(restTemplate.getForObject(BASE_API_URL + PAGE_PARAM + SECOND_PAGE_NUMBER, JobsWrapperDataDto.class)).thenReturn(jobsWrapperDataDto);

        List<JobDataDto> jobDataDtos = defaultArbeitNowClient.fetchJobsUntilPage(2);

        Assertions.assertNotNull(jobDataDtos);
        Assertions.assertEquals(4, jobDataDtos.size());
        verify(restTemplate).getForObject(BASE_API_URL + PAGE_PARAM + FIRST_PAGE_NUMBER, JobsWrapperDataDto.class);
        verify(restTemplate).getForObject(BASE_API_URL + PAGE_PARAM + SECOND_PAGE_NUMBER, JobsWrapperDataDto.class);
    }

}
