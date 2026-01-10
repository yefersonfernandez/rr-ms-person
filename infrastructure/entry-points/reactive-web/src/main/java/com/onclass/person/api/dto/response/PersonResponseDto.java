package com.onclass.person.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Person Response DTO")
public record PersonResponseDto(
        @Schema(description = "Unique identifier of the person", example = "1")
        Long id,

        @Schema(description = "Name of the person", example = "John Doe")
        String name,

        @Schema(description = "Email of the person", example = "john@example.com")
        String email,

        @Schema(description = "Age of the person", example = "30")
        Integer age
) {}
