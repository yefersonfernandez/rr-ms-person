package com.onclass.person.model.person.gateways;

import com.onclass.person.model.person.Person;
import reactor.core.publisher.Mono;

public interface PersonRepositoryPort {
    Mono<Person> savePerson(Person person);
    Mono<Person> findPersonById(Long personId);
}
