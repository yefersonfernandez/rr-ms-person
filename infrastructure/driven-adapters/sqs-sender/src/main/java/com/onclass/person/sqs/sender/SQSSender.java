package com.onclass.person.sqs.sender;

import com.onclass.person.port.sqs.SqsSenderPort;
import com.onclass.person.port.sqs.model.PersonEnrollMessage;
import com.onclass.person.sqs.sender.config.SQSSenderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;
import tools.jackson.databind.ObjectMapper;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements SqsSenderPort {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> sendPersonEnrollmentMessage(PersonEnrollMessage message) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(message))
                .map(rawMessage -> buildRequest(rawMessage, properties.personEnrollmentQueueUrl()))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnSuccess(response -> log.info(
                        "SQS_PUBLISH_SUCCESS | Enroll: (PersonId: {}) - (BootcampId: {})",
                        message.getPersonId(),
                        message.getBootcampId()
                ))
                .doOnError(error -> log.error(
                        "SQS_PUBLISH_ERROR | Enroll: (PersonId: {}) - (BootcampId: {})",
                        message.getPersonId(),
                        message.getBootcampId()
                ))
                .then();
    }

    private SendMessageRequest buildRequest(String message, String queueUrl) {
        return SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();
    }


}
