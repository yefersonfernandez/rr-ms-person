package com.onclass.person.usecase.person;

import com.onclass.person.enums.ExceptionMessages;
import com.onclass.person.exceptions.NotFoundException;
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

    public Mono<Person> getPersonById(Long personId) {
        return personRepositoryPort.findPersonById(personId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        ExceptionMessages.PERSON_NOT_FOUND.format(personId))));
    }
}
