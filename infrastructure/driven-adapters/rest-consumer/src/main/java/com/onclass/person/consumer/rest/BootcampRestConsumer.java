package com.onclass.person.consumer.rest;

import com.onclass.person.exceptions.ExternalServiceException;
import com.onclass.person.port.consumer.BootcampConsumerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BootcampRestConsumer implements BootcampConsumerPort {
    private static final String VALIDATE_CONFLICTS_URL = "/bootcamp/api/v1/bootcamps/{id}/conflicts";

    private final WebClient bootcampWebClient;

    @Override
    public Mono<Boolean> validateConflicts(Long newBootcampId ,List<Long> bootcampIds) {
        return bootcampWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(VALIDATE_CONFLICTS_URL)
                        .queryParam("ids", bootcampIds)
                        .build(newBootcampId))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new ExternalServiceException(errorBody)))
                )
                .bodyToMono(Boolean.class);
    }
}
