package com.onclass.person.api.openapi;

import com.onclass.person.api.dto.request.PersonRequestDto;
import com.onclass.person.api.dto.response.ApiResponseDto;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class PersonOpenApi {
    private static final String TAG = "Person";
    private static final String OPERATION_ID = "savePerson";
    private static final String OPERATION_DESC = "Create a new person";

    private static final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private static final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    private static final String CREATED_DESC = "Person successfully created";
    private static final String BAD_REQUEST_DESC = "Invalid request data or validation error";
    private static final String INTERNAL_ERROR_DESC = "Internal server error";
    private static final String REQUEST_BODY_DESC = "Person data to create";

    private static final String GET_PERSON_BY_ID_OPERATION_ID = "getPersonById";
    private static final String GET_PERSON_BY_ID_OPERATION_DESC = "Get a person by their unique identifier.";
    private static final String OK_CODE = String.valueOf(HttpStatus.OK.value());
    private static final String NOT_FOUND_CODE = String.valueOf(HttpStatus.NOT_FOUND.value());
    private static final String OK_DESC = "Person found successfully";
    private static final String NOT_FOUND_DESC = "Person not found";
    private static final String REQUEST_PARAM_PERSON_ID_DESC = "Unique identifier of the person to retrieve";
    private static final String GET_PARAM_NAME = "personId";

    public void savePerson(Builder builder) {
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
                        .implementation(PersonRequestDto.class))))
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
                .responseCode(INTERNAL_ERROR_CODE)
                .description(INTERNAL_ERROR_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))));
    }

    public void getPersonById(Builder builder) {
        builder
            .operationId(GET_PERSON_BY_ID_OPERATION_ID)
            .description(GET_PERSON_BY_ID_OPERATION_DESC)
            .tag(TAG)
            .parameter(parameterBuilder()
                    .name(GET_PARAM_NAME)
                    .description(REQUEST_PARAM_PERSON_ID_DESC)
                    .in(ParameterIn.PATH)
                    .required(true)
            )
            .response(responseBuilder()
                .responseCode(OK_CODE)
                .description(OK_DESC)
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
                        .implementation(ApiResponseDto.class))))
            .response(responseBuilder()
                .responseCode(INTERNAL_ERROR_CODE)
                .description(INTERNAL_ERROR_DESC)
                .content(contentBuilder()
                    .mediaType(MediaType.APPLICATION_JSON_VALUE)
                    .schema(schemaBuilder()
                        .implementation(ApiResponseDto.class))));
    }
}
