package com.onclass.person.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request DTO to enroll a person in a bootcamp")
public record BootcampPersonRequestDto(
        @Schema(description = "ID of the bootcamp", example = "1")
        String bootcampId,

        @Schema(description = "ID of the person", example = "1")
        String personId
) {}

