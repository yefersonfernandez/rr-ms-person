package com.onclass.person.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Person Microservice",
                description = "Manages persons and their bootcamp enrollments."
        )
)
public class SwaggerConfig {}
