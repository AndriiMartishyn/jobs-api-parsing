package com.martishyn.jobsapi.domain.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("jobs").pathsToMatch("/**").build();
    }

    @Bean
    OpenAPI customOpenAPI() {
        Contact myContact = new Contact();
        myContact.setName("Andrii Martishyn");
        myContact.setEmail("contactgently@gmail.com");
        return new OpenAPI().info(new Info().title("Jobs API")
                .version("1.0")
                .contact(myContact)
                .description("This is a Job Api for searching jobs"));
    }
}
