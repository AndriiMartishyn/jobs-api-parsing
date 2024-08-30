package com.martishyn.jobsapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class JobsRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobsRestApiApplication.class, args);
	}
}
