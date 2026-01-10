package com.onclass.person.api.openapi;

import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.onclass.person.api.dto.request.BootcampPersonRequestDto;
import com.onclass.person.api.dto.response.ApiResponseDto;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class BootcampPersonOpenApi {
    private static final String TAG = "BootcampPerson";
    private static final String OPERATION_ID = "enrollPerson";
    private static final String OPERATION_DESC = "Enroll a person in a bootcamp";

    private static final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private static final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String CONFLICT_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    private static final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());

    private static final String CREATED_DESC = "Person successfully enrolled in the bootcamp";
    private static final String BAD_REQUEST_DESC = "Invalid request data";
    private static final String CONFLICT_DESC = "The person is already enrolled in the bootcamp";
    private static final String NOT_FOUND_DESC = "The person does not exist";
    private static final String ENROLLMENT_LIMIT_DESC = "The person has reached the maximum number of bootcamp enrollments";
    private static final String REQUEST_BODY_DESC = "Person data to enroll";

    public void enrollPerson(Builder builder) {
        builder
            .operationId(OPERATION_ID)
            .description(OPERATION_DESC)
            .tag(TAG)
            .requestBody(requestBodyBuilder()
                .description(REQUEST_BODY_DESC)
                .required(true)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(BootcampPersonRequestDto.class))))
            .response(responseBuilder()
                .responseCode(CREATED_CODE)
                .description(CREATED_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))))
            .response(responseBuilder()
                .responseCode(BAD_REQUEST_CODE)
                .description(BAD_REQUEST_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))))
            .response(responseBuilder()
                .responseCode(BAD_REQUEST_CODE)
                .description(ENROLLMENT_LIMIT_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))))
            .response(responseBuilder()
                .responseCode(CONFLICT_CODE)
                .description(CONFLICT_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))))
            .response(responseBuilder()
                .responseCode(NOT_FOUND_CODE)
                .description(NOT_FOUND_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))));
    }
}
