package com.martishyn.jobsapi;

import com.martishyn.jobsapi.domain.service.ArbeitNowJsonProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class JobsRestApiApplication {
	private final ArbeitNowJsonProcessingService jobsConsumeService;

	public static void main(String[] args) {
		SpringApplication.run(JobsRestApiApplication.class, args);
	}
}
