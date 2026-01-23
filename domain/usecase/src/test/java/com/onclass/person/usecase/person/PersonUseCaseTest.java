package com.onclass.person.usecase.person;

import com.onclass.person.enums.ExceptionMessages;
import com.onclass.person.exceptions.NotFoundException;
import com.onclass.person.model.person.Person;
import com.onclass.person.model.person.gateways.PersonRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonUseCaseTest {

    @Mock
    private PersonRepositoryPort personRepositoryPort;
    @InjectMocks
    private PersonUseCase personUseCase;

    @Test
    void savePerson_shouldSaveSuccessfully() {
        Person person = new Person();
        when(personRepositoryPort.savePerson(any())).thenReturn(Mono.just(person));

        StepVerifier.create(personUseCase.savePerson(person))
                .expectNext(person)
                .verifyComplete();
    }

    @Test
    void getPersonById_shouldReturnPersonWhenFound() {
        Long personId = 1L;
        Person person = new Person();
        when(personRepositoryPort.findPersonById(personId)).thenReturn(Mono.just(person));

        StepVerifier.create(personUseCase.getPersonById(personId))
                .expectNext(person)
                .verifyComplete();
    }

    @Test
    void getPersonById_shouldThrowNotFoundExceptionWhenNotFound() {
        Long personId = 2L;
        when(personRepositoryPort.findPersonById(personId)).thenReturn(Mono.empty());

        StepVerifier.create(personUseCase.getPersonById(personId))
                .expectErrorMatches(throwable -> throwable instanceof NotFoundException
                        && throwable.getMessage().equals(ExceptionMessages.PERSON_NOT_FOUND.format(personId)))
                .verify();
    }
}

