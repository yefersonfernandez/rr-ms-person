package com.onclass.person.usecase.person;

import com.onclass.person.model.person.Person;
import com.onclass.person.model.person.gateways.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PersonUseCase {
    private final PersonRepositoryPort personRepositoryPort;

    public Mono<Person> savePerson(Person person) {
        return personRepositoryPort.savePerson(person);
    }
}
