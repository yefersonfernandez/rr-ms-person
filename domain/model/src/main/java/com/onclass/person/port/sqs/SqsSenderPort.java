package com.onclass.person.port.sqs;

import com.onclass.person.port.sqs.model.PersonEnrollMessage;
import reactor.core.publisher.Mono;

public interface SqsSenderPort {
    Mono<Void> sendPersonEnrollmentMessage(PersonEnrollMessage message);
}
