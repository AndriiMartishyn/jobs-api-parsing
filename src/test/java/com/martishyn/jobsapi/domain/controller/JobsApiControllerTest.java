package com.martishyn.jobsapi.domain.controller;

import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import com.martishyn.jobsapi.domain.service.server.JobsRestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = JobsApiController.class)
public class JobsApiControllerTest {

    private static final String RESOURCE_ENDPOINT = "/v1/jobs";

    private static final String RECENT_JOBS_ENDPOINT = "/recent";

    private static final String LOCATION_STATISTICS_ENDPOINT = "/statistics";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobsRestService jobsRestService;

    @MockBean
    private LocationStatisticsDto locationStatisticsDto;

    private List<JobDataDto> jobDataDtoList;

    private PageRequest pageRequestWithTitleSort;

    private PageRequest pageRequestWithCreatedAtSort;

    private PageRequest simplePageRequestWithSize;

    @BeforeEach
    void setUp() {
        JobDataDto jobDataDto1 = JobDataDto.builder()
                .title("test-title-1")
                .createdAt(125L)
                .build();
        JobDataDto jobDataDto2 = JobDataDto.builder()
                .title("test-title-2")
                .createdAt(130L)
                .build();
        jobDataDtoList = List.of(jobDataDto1, jobDataDto2);
        pageRequestWithTitleSort = PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, "title"));
        pageRequestWithCreatedAtSort = PageRequest.of(1, 1, Sort.by(Sort.Direction.DESC, "createdAt"));
        simplePageRequestWithSize = PageRequest.ofSize(10);
    }

    @Test
    void shouldBeAbleToReturnListOfJobsWithPagingAndSortingParamPassed() throws Exception {
        when(jobsRestService.findJobsPaginatedAndSorted(pageRequestWithTitleSort)).thenReturn(jobDataDtoList);

        ResultActions result = mockMvc.perform(get(RESOURCE_ENDPOINT + "?currentPage=1&pageSize=1&sortBy=title,asc"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.['data'].[0].title").value(jobDataDtoList.get(0).getTitle()))
                .andExpect(jsonPath("$.['data'].[0].created_at").value(jobDataDtoList.get(0).getCreatedAt()))
                .andExpect(jsonPath("$.['data'].[1].title").value(jobDataDtoList.get(1).getTitle()))
                .andExpect(jsonPath("$.['data'].[1].created_at").value(jobDataDtoList.get(1).getCreatedAt()))
                .andDo(print());

        verify(jobsRestService).findJobsPaginatedAndSorted(pageRequestWithTitleSort);
    }

    @Test
    void shouldBeAbleToReturnOrderedListOfJobsByCreateDateWithPagingAndSortingParamPassed() throws Exception {
        when(jobsRestService.findJobsPaginatedAndSorted(pageRequestWithCreatedAtSort)).thenReturn(List.of(jobDataDtoList.getLast(), jobDataDtoList.getFirst()));

        ResultActions result = mockMvc.perform(get(RESOURCE_ENDPOINT + "?currentPage=1&pageSize=1&sortBy=createdAt,desc"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.['data'].[0].title").value(jobDataDtoList.get(1).getTitle()))
                .andExpect(jsonPath("$.['data'].[0].created_at").value(jobDataDtoList.get(1).getCreatedAt()))
                .andExpect(jsonPath("$.['data'].[1].title").value(jobDataDtoList.get(0).getTitle()))
                .andExpect(jsonPath("$.['data'].[1].created_at").value(jobDataDtoList.get(0).getCreatedAt()))
                .andDo(print());

        verify(jobsRestService).findJobsPaginatedAndSorted(pageRequestWithCreatedAtSort);
    }

    @Test
    void shouldReturnMostRecentlyAddedJobsWithSizeParamPassed() throws Exception {
        when(jobsRestService.findMostRecentJobs(simplePageRequestWithSize)).thenReturn(jobDataDtoList);

        ResultActions result = mockMvc.perform(get(RESOURCE_ENDPOINT + RECENT_JOBS_ENDPOINT));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.['data'].[0].title").value(jobDataDtoList.get(0).getTitle()))
                .andExpect(jsonPath("$.['data'].[0].created_at").value(jobDataDtoList.get(0).getCreatedAt()))
                .andExpect(jsonPath("$.['data'].[1].title").value(jobDataDtoList.get(1).getTitle()))
                .andExpect(jsonPath("$.['data'].[1].created_at").value(jobDataDtoList.get(1).getCreatedAt()))
                .andDo(print());

        verify(jobsRestService).findMostRecentJobs(simplePageRequestWithSize);
    }

    @Test
    void shouldReturnLocationStatisticsDtoResponse() throws Exception {
        when(jobsRestService.findStatisticsByLocation()).thenReturn(Collections.singletonList(locationStatisticsDto));

        ResultActions result = mockMvc.perform(get(RESOURCE_ENDPOINT + LOCATION_STATISTICS_ENDPOINT));

        result.andExpect(status().isOk())
                .andDo(print());

        verify(jobsRestService).findStatisticsByLocation();
    }
}
