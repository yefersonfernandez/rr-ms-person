package com.onclass.person.usecase.bootcampperson;

import com.onclass.person.enums.ExceptionMessages;
import com.onclass.person.exceptions.BootcampCollisionException;
import com.onclass.person.exceptions.EnrollmentLimitException;
import com.onclass.person.exceptions.NotFoundException;
import com.onclass.person.model.bootcampperson.BootcampPerson;
import com.onclass.person.model.bootcampperson.gateways.BootcampPersonRepositoryPort;
import com.onclass.person.model.person.gateways.PersonRepositoryPort;
import com.onclass.person.port.consumer.BootcampConsumerPort;
import com.onclass.person.port.sqs.SqsSenderPort;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.onclass.person.constants.PersonConstants.LIMIT_BOOTCAMP_ENROLLMENTS;
import static com.onclass.person.usecase.utils.PersonUtils.buildPersonMessage;

@RequiredArgsConstructor
public class BootcampPersonUseCase {
    private final PersonRepositoryPort personRepositoryPort;
    private final BootcampPersonRepositoryPort bootcampPersonRepositoryPort;
    private final BootcampConsumerPort bootcampConsumerPort;
    private final SqsSenderPort sqsSenderPort;

    public Mono<BootcampPerson> enrollPerson(BootcampPerson bootcampPerson) {
        return personRepositoryPort.findPersonById(bootcampPerson.getPersonId())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        ExceptionMessages.PERSON_NOT_FOUND.format(bootcampPerson.getPersonId()))))
                .flatMap(person -> bootcampPersonRepositoryPort.findAllByPersonId(bootcampPerson.getPersonId())
                        .map(BootcampPerson::getBootcampId)
                        .collectList())
                .filter(currentIds -> currentIds.size() < LIMIT_BOOTCAMP_ENROLLMENTS)
                .switchIfEmpty(Mono.error(new EnrollmentLimitException(
                        ExceptionMessages.ENROLLMENT_LIMIT_EXCEEDED.format(LIMIT_BOOTCAMP_ENROLLMENTS))))
                .flatMap(currentIds ->
                        bootcampConsumerPort.validateConflicts(bootcampPerson.getBootcampId(), currentIds)
                                .filter(Boolean::booleanValue)
                                .switchIfEmpty(Mono.error(new BootcampCollisionException(
                                        ExceptionMessages.BOOTCAMP_COLLISION.format(bootcampPerson.getBootcampId()))))
                )
                .then(bootcampPersonRepositoryPort.save(bootcampPerson)
                        .doOnSuccess(this::notifyPersonEnrollment)
                );
    }

    private void notifyPersonEnrollment(BootcampPerson bootcampPerson) {
        Mono.fromSupplier(() -> buildPersonMessage(bootcampPerson))
                .flatMap(sqsSenderPort::sendPersonEnrollmentMessage)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}
