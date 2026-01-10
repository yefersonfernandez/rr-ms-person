package com.onclass.person.r2dbc.bootcampperson;

import com.onclass.person.r2dbc.entity.BootcampPersonEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BootcampPersonRepository extends ReactiveCrudRepository<BootcampPersonEntity, Long>, ReactiveQueryByExampleExecutor<BootcampPersonEntity> {
    Flux<BootcampPersonEntity> findAllByPersonId(Long personId);
}
