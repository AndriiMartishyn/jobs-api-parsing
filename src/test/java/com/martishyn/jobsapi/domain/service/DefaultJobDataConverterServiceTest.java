package com.martishyn.jobsapi.domain.service;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.service.impl.DefaultJobDataConverterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DefaultJobDataConverterServiceTest {

    private static final String EMPTY_STRING = "";

    private static JobDataDto jobDataResponse1;

    private static JobDataDto jobDataResponse2;

    private static JobDataConverterService jobDataConverter;

    private static List<JobDataDto> jobDataResponseList;

    private static final long EARLY_EPOCH_TIME = Instant.now().getEpochSecond() + 1;

    private static final long LATE_EPOCH_TIME = Instant.now().getEpochSecond() + 2;

    @BeforeAll
    static void init() {
        jobDataConverter = new DefaultJobDataConverterService();
        jobDataResponse1 = new JobDataDto();
        jobDataResponse1.setSlugName("test-1");
        jobDataResponse1.setCompanyName("test-company");
        jobDataResponse1.setTitle("test-title");
        jobDataResponse1.setDescription("test - description");
        jobDataResponse1.setRemote(Boolean.TRUE);
        jobDataResponse1.setUrl("test-url");
        jobDataResponse1.setJobTypes(List.of("Test1", "Test2"));
        jobDataResponse1.setTags(List.of("Tag1", "Tag2"));
        jobDataResponse1.setLocation("test-location");
        jobDataResponse1.setCreatedAt(EARLY_EPOCH_TIME);
        jobDataResponse2 = new JobDataDto();
        jobDataResponse2.setRemote(Boolean.TRUE);
        jobDataResponse2.setCreatedAt(LATE_EPOCH_TIME);
        jobDataResponseList = new ArrayList<>();
        jobDataResponseList.add(jobDataResponse1);
        jobDataResponseList.add(jobDataResponse2);
    }

    @DisplayName("convert-response-obj-to-dmo")
    @Test
    void shouldConvertResponseJobObjectToDmoObject_WhenValidInput() {
        JobDataDmo actualJobDataDmo = jobDataConverter.convertSingleResponseDataToDmo(jobDataResponse1);

        Assertions.assertNotNull(actualJobDataDmo);
        Assertions.assertEquals(jobDataResponse1.getSlugName(), actualJobDataDmo.getSlugName());
        Assertions.assertEquals(jobDataResponse1.getCompanyName(), actualJobDataDmo.getCompanyName());
        Assertions.assertEquals(jobDataResponse1.getTitle(), actualJobDataDmo.getTitle());
        Assertions.assertEquals(jobDataResponse1.getDescription(), actualJobDataDmo.getDescription());
        Assertions.assertEquals("true", actualJobDataDmo.getRemote());
        Assertions.assertEquals(jobDataResponse1.getUrl(), actualJobDataDmo.getUrl());
        Assertions.assertEquals("Test1, Test2", actualJobDataDmo.getJobTypes());
        Assertions.assertEquals("Tag1, Tag2", actualJobDataDmo.getTags());
        Assertions.assertEquals(jobDataResponse1.getLocation(), actualJobDataDmo.getLocation());
        Assertions.assertEquals(jobDataResponse1.getCreatedAt(), actualJobDataDmo.getCreatedAt());
    }

    @DisplayName("convert-response-obj-to-dmo-with-null-fields")
    @Test
    void shouldConvertResponseJobObjectToDmoObject_WhenTagsAndJobTypesCollectionsAreEmpty() {
        jobDataResponse1.setJobTypes(null);
        jobDataResponse1.setTags(null);
        JobDataDmo actualJobDataDmo = jobDataConverter.convertSingleResponseDataToDmo(jobDataResponse1);

        Assertions.assertEquals(EMPTY_STRING, actualJobDataDmo.getJobTypes());
        Assertions.assertEquals(EMPTY_STRING, actualJobDataDmo.getTags());
    }

    @DisplayName("convert-response-obj-to-dmo-and-order-by-create-date")
    @Test
    void shouldConvertResponseJobObjectToDmoObjectAndSort_WhenPassingObjectWithComparableCreateTimeStamp() {
        List<JobDataDmo> jobDataDmos = jobDataConverter.convertResponseDataToDmoAndOrderByCreateDate(jobDataResponseList);

        Assertions.assertNotNull(jobDataDmos);
        Assertions.assertEquals(jobDataResponseList.size(), jobDataDmos.size());
        Assertions.assertEquals(LATE_EPOCH_TIME, jobDataDmos.getFirst().getCreatedAt());
        Assertions.assertEquals(EARLY_EPOCH_TIME, jobDataDmos.getLast().getCreatedAt());
    }
}
