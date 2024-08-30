package com.martishyn.jobsapi.domain.repository;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import com.martishyn.jobsapi.domain.dto.LocationStatisticsDto;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface JobDataRepository extends JpaRepository<JobDataDmo, Long> {

    @Query("""
            SELECT j FROM JobDataDmo j
            ORDER BY j.createdAt DESC""")
    List<JobDataDmo> findMostRecentJobs(Pageable pageable);

    @Query("""
            SELECT NEW com.martishyn.jobsapi.domain.dto.LocationStatisticsDto(j.location, COUNT(j))
            from JobDataDmo j
            group by j.location""")
    List<LocationStatisticsDto> findLocationStatistics();
}
