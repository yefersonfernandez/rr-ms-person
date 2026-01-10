package com.onclass.person.r2dbc.person;

import com.onclass.person.r2dbc.entity.PersonEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Long>, ReactiveQueryByExampleExecutor<PersonEntity> {
}
