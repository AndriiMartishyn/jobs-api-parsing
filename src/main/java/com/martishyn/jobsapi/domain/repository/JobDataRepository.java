package com.martishyn.jobsapi.domain.repository;

import com.martishyn.jobsapi.domain.dmo.JobDataDmo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobDataRepository extends JpaRepository<JobDataDmo, Long> {
}
