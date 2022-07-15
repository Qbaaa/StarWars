package com.qbaaa.StarWars.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("StarWars API")
                        .description("StarWars REST API")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Qbaaa")
                                .email("jakubkowalczyjk@gmail")));
    }

    @Bean
    public GroupedOpenApi appOpenApi() {
        return GroupedOpenApi.builder()
                .group("app")
                .packagesToScan("com.qbaaa.StarWars.controllers")
                .build();
    }
}

