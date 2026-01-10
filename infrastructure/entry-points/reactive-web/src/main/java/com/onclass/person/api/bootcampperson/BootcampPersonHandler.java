package com.onclass.person.api.bootcampperson;

import com.onclass.person.api.dto.request.BootcampPersonRequestDto;
import com.onclass.person.api.mapper.BootcampPersonMapper;
import com.onclass.person.enums.ExceptionStatusCode;
import com.onclass.person.usecase.bootcampperson.BootcampPersonUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.onclass.person.api.constants.BootcampPersonLogMessages.ENROLL_PERSON_RESPONSE;
import static com.onclass.person.api.utils.HandlersResponseUtil.buildBodySuccessResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class BootcampPersonHandler {
    private final BootcampPersonUseCase bootcampPersonUseCase;
    private final BootcampPersonMapper bootcampPersonMapper;

    public Mono<ServerResponse> listenEnrollPerson(ServerRequest request) {
        return request.bodyToMono(BootcampPersonRequestDto.class)
                .map(bootcampPersonMapper::toModel)
                .flatMap(bootcampPersonUseCase::enrollPerson)
                .map(bootcampPersonMapper::toBootcampPersonResponseDto)
                .doOnNext(dto -> log.info(ENROLL_PERSON_RESPONSE, dto))
                .flatMap(result -> ServerResponse.status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buildBodySuccessResponse(ExceptionStatusCode.CREATED.status(), result)));
    }
}
