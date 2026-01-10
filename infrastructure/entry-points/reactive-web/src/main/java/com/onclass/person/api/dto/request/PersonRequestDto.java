package com.onclass.person.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Person Request DTO")
public record PersonRequestDto(
        @NotBlank(message = "Name is required")
        @Schema(description = "Name of the person", example = "John Doe")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Schema(description = "Email of the person", example = "john@example.com")
        String email,

        @NotNull(message = "Age is required")
        @Schema(description = "Age of the person", example = "30")
        Integer age
) {}
