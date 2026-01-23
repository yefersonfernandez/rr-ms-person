package com.onclass.person.r2dbc.bootcampperson;

import com.onclass.person.model.bootcampperson.BootcampPerson;
import com.onclass.person.r2dbc.entity.BootcampPersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampPersonRepositoryAdapterTest {

    @InjectMocks
    private BootcampPersonRepositoryAdapter adapter;
    @Mock
    private BootcampPersonRepository repository;
    @Mock
    private ObjectMapper mapper;

    private BootcampPerson bootcampPerson;
    private BootcampPersonEntity bootcampPersonEntity;

    @BeforeEach
    void setUp() {
        bootcampPerson = new BootcampPerson();
        bootcampPersonEntity = new BootcampPersonEntity();
    }

    @Test
    @DisplayName("save should save and return BootcampPerson")
    void save_shouldSaveAndReturnBootcampPerson() {
        when(mapper.map(bootcampPerson, BootcampPersonEntity.class)).thenReturn(bootcampPersonEntity);
        when(repository.save(bootcampPersonEntity)).thenReturn(Mono.just(bootcampPersonEntity));
        when(mapper.map(bootcampPersonEntity, BootcampPerson.class)).thenReturn(bootcampPerson);

        StepVerifier.create(adapter.save(bootcampPerson))
                .expectNext(bootcampPerson)
                .verifyComplete();
    }

    @Test
    @DisplayName("findAllByPersonId should return BootcampPersons when found")
    void findAllByPersonId_shouldReturnBootcampPersons() {
        Long personId = 1L;
        when(repository.findAllByPersonId(personId)).thenReturn(Flux.just(bootcampPersonEntity));
        when(mapper.map(bootcampPersonEntity, BootcampPerson.class)).thenReturn(bootcampPerson);

        StepVerifier.create(adapter.findAllByPersonId(personId))
                .expectNext(bootcampPerson)
                .verifyComplete();
    }

    @Test
    @DisplayName("findAllByPersonId should return empty when none found")
    void findAllByPersonId_shouldReturnEmpty() {
        Long personId = 2L;
        when(repository.findAllByPersonId(personId)).thenReturn(Flux.empty());

        StepVerifier.create(adapter.findAllByPersonId(personId))
                .verifyComplete();
    }
}

