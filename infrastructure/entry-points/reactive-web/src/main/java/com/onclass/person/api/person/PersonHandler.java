package com.onclass.person.api.person;

import com.onclass.person.api.dto.request.PersonRequestDto;
import com.onclass.person.api.mapper.PersonMapper;
import com.onclass.person.api.utils.ValidatorUtil;
import com.onclass.person.enums.ExceptionStatusCode;
import com.onclass.person.usecase.person.PersonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.onclass.person.api.utils.HandlersResponseUtil.buildBodySuccessResponse;
import static org.springframework.http.HttpStatus.CREATED;

@Component
@RequiredArgsConstructor
public class PersonHandler {
    private final PersonUseCase personUseCase;
    private final PersonMapper personMapper;
    private final ValidatorUtil validatorUtil;

    public Mono<ServerResponse> listenSavePerson(ServerRequest request) {
        return request.bodyToMono(PersonRequestDto.class)
                .flatMap(validatorUtil::validate)
                .map(personMapper::toModel)
                .flatMap(personUseCase::savePerson)
                .map(personMapper::toPersonResponseDto)
                .flatMap(savedPerson -> ServerResponse.status(CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(buildBodySuccessResponse(ExceptionStatusCode.CREATED.status(), savedPerson))
                );
    }
}
