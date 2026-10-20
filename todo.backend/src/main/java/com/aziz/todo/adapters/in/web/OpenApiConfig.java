package com.aziz.todo.adapters.in.web;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI todoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo List API")
                        .version("1.0.0")
                        .description(
                                "REST API for managing tasks, built with "
                                + "Spring Boot and Hexagonal Architecture."
                        ));
    }
}
