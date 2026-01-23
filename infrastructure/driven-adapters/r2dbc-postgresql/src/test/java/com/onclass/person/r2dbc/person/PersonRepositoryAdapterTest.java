package com.onclass.person.r2dbc.person;

import com.onclass.person.model.person.Person;
import com.onclass.person.r2dbc.entity.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryAdapterTest {

    @InjectMocks
    private PersonRepositoryAdapter adapter;
    @Mock
    private PersonRepository repository;
    @Mock
    private ObjectMapper mapper;

    private Person person;
    private PersonEntity personEntity;

    @BeforeEach
    void setUp() {
        person = new Person();
        personEntity = new PersonEntity();
    }

    @Test
    @DisplayName("savePerson should save and return person")
    void savePerson_shouldSaveAndReturnPerson() {
        when(mapper.map(person, PersonEntity.class)).thenReturn(personEntity);
        when(repository.save(personEntity)).thenReturn(Mono.just(personEntity));
        when(mapper.map(personEntity, Person.class)).thenReturn(person);

        StepVerifier.create(adapter.savePerson(person))
                .expectNext(person)
                .verifyComplete();
    }

    @Test
    @DisplayName("findPersonById should return person when found")
    void findPersonById_shouldReturnPerson() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Mono.just(personEntity));
        when(mapper.map(personEntity, Person.class)).thenReturn(person);

        StepVerifier.create(adapter.findPersonById(id))
                .expectNext(person)
                .verifyComplete();
    }

    @Test
    @DisplayName("findPersonById should complete empty when not found")
    void findPersonById_shouldReturnEmptyWhenNotFound() {
        Long id = 2L;
        when(repository.findById(id)).thenReturn(Mono.empty());

        StepVerifier.create(adapter.findPersonById(id))
                .verifyComplete();
    }
}

