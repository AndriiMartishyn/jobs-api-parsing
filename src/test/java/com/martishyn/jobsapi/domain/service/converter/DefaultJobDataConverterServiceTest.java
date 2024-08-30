package com.martishyn.jobsapi.domain.service.converter;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.JobDataDto;
import com.martishyn.jobsapi.domain.service.converter.impl.DefaultJobDataConverterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultJobDataConverterServiceTest {

    private static final String EMPTY_STRING = "";

    private static JobDataDto testJobDataDtoObject_1;

    private static JobDataDto testJobDataDtoObject_2;

    private static JobDataDmo testJobDataDmoObject;

    private static JobDataConverterService jobDataConverter;

    private static List<JobDataDto> jobDataResponseList;

    private static final long EARLY_EPOCH_TIME = Instant.now().getEpochSecond() + 1;

    private static final long LATE_EPOCH_TIME = Instant.now().getEpochSecond() + 2;

    @BeforeAll
    static void init() {
        jobDataConverter = new DefaultJobDataConverterService();
        testJobDataDtoObject_1 = new JobDataDto();
        testJobDataDtoObject_1.setSlugName("test-1");
        testJobDataDtoObject_1.setCompanyName("test-company");
        testJobDataDtoObject_1.setTitle("test-title");
        testJobDataDtoObject_1.setDescription("test - description");
        testJobDataDtoObject_1.setRemote(Boolean.TRUE);
        testJobDataDtoObject_1.setUrl("test-url");
        testJobDataDtoObject_1.setJobTypes(List.of("Test1", "Test2"));
        testJobDataDtoObject_1.setTags(List.of("Tag1", "Tag2"));
        testJobDataDtoObject_1.setLocation("test-location");
        testJobDataDtoObject_1.setCreatedAt(EARLY_EPOCH_TIME);
        testJobDataDtoObject_2 = new JobDataDto();
        testJobDataDtoObject_2.setRemote(Boolean.TRUE);
        testJobDataDtoObject_2.setCreatedAt(LATE_EPOCH_TIME);
        jobDataResponseList = new ArrayList<>();
        jobDataResponseList.add(testJobDataDtoObject_1);
        jobDataResponseList.add(testJobDataDtoObject_2);
        testJobDataDmoObject = new JobDataDmo();
        testJobDataDmoObject.setSlugName("test-1");
        testJobDataDmoObject.setCompanyName("test-company");
        testJobDataDmoObject.setTitle("test-title");
        testJobDataDmoObject.setDescription("test - description");
        testJobDataDmoObject.setRemote("true");
        testJobDataDmoObject.setUrl("test-url");
        testJobDataDmoObject.setJobTypes("Test1, Test2");
        testJobDataDmoObject.setTags("Tag1, Tag2");
        testJobDataDmoObject.setLocation("test-location");
        testJobDataDmoObject.setCreatedAt(EARLY_EPOCH_TIME);

    }

    @DisplayName("convert-dto-obj-to-dmo")
    @Test
    void shouldConvertDtoJobObjectToDmoObject_WhenValidInput() {
        JobDataDmo actualJobDataDmo = jobDataConverter.convertJobDtoToJobDmo(testJobDataDtoObject_1);

        Assertions.assertNotNull(actualJobDataDmo);
        Assertions.assertEquals(testJobDataDtoObject_1.getSlugName(), actualJobDataDmo.getSlugName());
        Assertions.assertEquals(testJobDataDtoObject_1.getCompanyName(), actualJobDataDmo.getCompanyName());
        Assertions.assertEquals(testJobDataDtoObject_1.getTitle(), actualJobDataDmo.getTitle());
        Assertions.assertEquals(testJobDataDtoObject_1.getDescription(), actualJobDataDmo.getDescription());
        Assertions.assertEquals("true", actualJobDataDmo.getRemote());
        Assertions.assertEquals(testJobDataDtoObject_1.getUrl(), actualJobDataDmo.getUrl());
        Assertions.assertEquals("Test1, Test2", actualJobDataDmo.getJobTypes());
        Assertions.assertEquals("Tag1, Tag2", actualJobDataDmo.getTags());
        Assertions.assertEquals(testJobDataDtoObject_1.getLocation(), actualJobDataDmo.getLocation());
        Assertions.assertEquals(testJobDataDtoObject_1.getCreatedAt(), actualJobDataDmo.getCreatedAt());
    }

    @DisplayName("convert-dto-obj-to-dmo-with-null-fields")
    @Test
    void shouldConvertDtoJobObjectToDmoObject_WhenTagsAndJobTypesCollectionsAreEmpty() {
        testJobDataDtoObject_1.setJobTypes(null);
        testJobDataDtoObject_1.setTags(null);
        JobDataDmo actualJobDataDmo = jobDataConverter.convertJobDtoToJobDmo(testJobDataDtoObject_1);

        Assertions.assertEquals(EMPTY_STRING, actualJobDataDmo.getJobTypes());
        Assertions.assertEquals(EMPTY_STRING, actualJobDataDmo.getTags());
    }

    @DisplayName("convert-dto-obj-to-dmo-and-order-by-create-date")
    @Test
    void shouldConvertDtoJobObjectToDmoObjectAndSort_WhenPassingObjectWithComparableCreateTimeStamp() {
        List<JobDataDmo> jobDataDmos = jobDataConverter.convertJobDtoToJobDmoAndOrderByCreateDate(jobDataResponseList);

        Assertions.assertNotNull(jobDataDmos);
        Assertions.assertEquals(jobDataResponseList.size(), jobDataDmos.size());
        Assertions.assertEquals(LATE_EPOCH_TIME, jobDataDmos.getFirst().getCreatedAt());
        Assertions.assertEquals(EARLY_EPOCH_TIME, jobDataDmos.getLast().getCreatedAt());
    }

    @DisplayName("convert-dto-obj-to-dmo")
    @Test
    void shouldConvertResponseJobObjectToDmoObject_WhenValidInput() {
        JobDataDto actualJobDataDto = jobDataConverter.convertJobDmoToJobDto(testJobDataDmoObject);

        Assertions.assertNotNull(actualJobDataDto);
        Assertions.assertEquals(testJobDataDmoObject.getSlugName(), actualJobDataDto.getSlugName());
        Assertions.assertEquals(testJobDataDmoObject.getCompanyName(), actualJobDataDto.getCompanyName());
        Assertions.assertEquals(testJobDataDmoObject.getTitle(), actualJobDataDto.getTitle());
        Assertions.assertEquals(testJobDataDmoObject.getDescription(), actualJobDataDto.getDescription());
        Assertions.assertEquals(Boolean.TRUE, actualJobDataDto.getRemote());
        Assertions.assertEquals(testJobDataDmoObject.getUrl(), actualJobDataDto.getUrl());
        Assertions.assertEquals(Collections.singletonList("Test1, Test2"), actualJobDataDto.getJobTypes());
        Assertions.assertEquals(Collections.singletonList("Tag1, Tag2"), actualJobDataDto.getTags());
        Assertions.assertEquals(testJobDataDmoObject.getLocation(), actualJobDataDto.getLocation());
        Assertions.assertEquals(testJobDataDmoObject.getCreatedAt(), actualJobDataDto.getCreatedAt());
    }

}
