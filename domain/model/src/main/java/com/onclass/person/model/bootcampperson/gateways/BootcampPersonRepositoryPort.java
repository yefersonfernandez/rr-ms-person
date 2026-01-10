package com.onclass.person.model.bootcampperson.gateways;

import com.onclass.person.model.bootcampperson.BootcampPerson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BootcampPersonRepositoryPort {
    Flux<BootcampPerson> findAllByPersonId(Long personId);
    Mono<BootcampPerson> save(BootcampPerson bootcampPerson);
}

