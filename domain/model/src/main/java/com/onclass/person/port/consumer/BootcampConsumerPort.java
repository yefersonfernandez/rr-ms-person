package com.onclass.person.port.consumer;

import reactor.core.publisher.Mono;

import java.util.List;

public interface BootcampConsumerPort {
    Mono<Boolean> validateConflicts(Long newBootcampId ,List<Long> bootcampIds);
}
