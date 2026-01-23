package com.onclass.person.usecase.bootcampperson;

import com.onclass.person.exceptions.BootcampCollisionException;
import com.onclass.person.exceptions.EnrollmentLimitException;
import com.onclass.person.exceptions.NotFoundException;
import com.onclass.person.model.bootcampperson.BootcampPerson;
import com.onclass.person.model.bootcampperson.gateways.BootcampPersonRepositoryPort;
import com.onclass.person.model.person.Person;
import com.onclass.person.model.person.gateways.PersonRepositoryPort;
import com.onclass.person.port.consumer.BootcampConsumerPort;
import com.onclass.person.port.sqs.SqsSenderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BootcampPersonUseCaseTest {
    @Mock
    private PersonRepositoryPort personRepositoryPort;
    @Mock
    private BootcampPersonRepositoryPort bootcampPersonRepositoryPort;
    @Mock
    private BootcampConsumerPort bootcampConsumerPort;
    @Mock
    private SqsSenderPort personEnrollmentSenderPort;

    @InjectMocks
    private BootcampPersonUseCase bootcampPersonUseCase;

    private BootcampPerson bootcampPerson;
    private static final Long PERSON_ID = 1L;
    private static final Long BOOTCAMP_ID = 10L;

    @BeforeEach
    void setUp() {
        bootcampPerson = BootcampPerson.builder()
                .personId(PERSON_ID)
                .bootcampId(BOOTCAMP_ID)
                .build();
    }

    @Test
    @DisplayName("Should throw NotFoundException when person does not exist")
    void enrollPerson_shouldThrowNotFoundExceptionWhenPersonNotFound() {
        when(personRepositoryPort.findPersonById(PERSON_ID)).thenReturn(Mono.empty());
        lenient().when(bootcampPersonRepositoryPort.save(any())).thenReturn(Mono.empty());

        StepVerifier.create(bootcampPersonUseCase.enrollPerson(bootcampPerson))
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw EnrollmentLimitException when limit is exceeded")
    void enrollPerson_shouldThrowEnrollmentLimitExceptionWhenLimitExceeded() {
        Person mockPerson = new Person();
        when(personRepositoryPort.findPersonById(PERSON_ID)).thenReturn(Mono.just(mockPerson));
        when(bootcampPersonRepositoryPort.findAllByPersonId(PERSON_ID)).thenReturn(Flux.just(
                BootcampPerson.builder().bootcampId(1L).build(),
                BootcampPerson.builder().bootcampId(2L).build(),
                BootcampPerson.builder().bootcampId(3L).build(),
                BootcampPerson.builder().bootcampId(4L).build(),
                BootcampPerson.builder().bootcampId(5L).build()
        ));

        lenient().when(bootcampConsumerPort.validateConflicts(anyLong(), anyList())).thenReturn(Mono.empty());
        lenient().when(bootcampPersonRepositoryPort.save(any())).thenReturn(Mono.empty());

        StepVerifier.create(bootcampPersonUseCase.enrollPerson(bootcampPerson))
                .expectError(EnrollmentLimitException.class)
                .verify();
    }

    @Test
    @DisplayName("Should throw BootcampCollisionException when schedule conflicts")
    void enrollPerson_shouldThrowBootcampCollisionExceptionWhenConflict() {
        Person mockPerson = new Person();
        when(personRepositoryPort.findPersonById(PERSON_ID)).thenReturn(Mono.just(mockPerson));
        when(bootcampPersonRepositoryPort.findAllByPersonId(PERSON_ID)).thenReturn(Flux.just(
                BootcampPerson.builder().bootcampId(1L).build()
        ));

        when(bootcampConsumerPort.validateConflicts(eq(BOOTCAMP_ID), anyList())).thenReturn(Mono.just(false));
        lenient().when(bootcampPersonRepositoryPort.save(any())).thenReturn(Mono.empty());

        StepVerifier.create(bootcampPersonUseCase.enrollPerson(bootcampPerson))
                .expectError(BootcampCollisionException.class)
                .verify();
    }

    @Test
    @DisplayName("Should save successfully")
    void enrollPerson_shouldSaveSuccessfully() {
        Person mockPerson = new Person();
        when(personRepositoryPort.findPersonById(PERSON_ID)).thenReturn(Mono.just(mockPerson));
        when(bootcampPersonRepositoryPort.findAllByPersonId(PERSON_ID)).thenReturn(Flux.empty());
        when(bootcampConsumerPort.validateConflicts(anyLong(), anyList())).thenReturn(Mono.just(true));
        when(bootcampPersonRepositoryPort.save(any())).thenReturn(Mono.just(bootcampPerson));
        lenient().when(personEnrollmentSenderPort.sendPersonEnrollmentMessage(any())).thenReturn(Mono.empty());

        StepVerifier.create(bootcampPersonUseCase.enrollPerson(bootcampPerson))
                .expectNext(bootcampPerson)
                .verifyComplete();
    }
}