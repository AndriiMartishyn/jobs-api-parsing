package com.martishyn.jobsapi;

import com.martishyn.jobsapi.domain.consume.service.JobsConsumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class JobsRestApiApplication {
	private final JobsConsumeService jobsConsumeService;

	public static void main(String[] args) {
		SpringApplication.run(JobsRestApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			System.out.println(jobsConsumeService.fetchDataFromApi());
		};
	}

}
