package com.onclass.person.r2dbc.bootcampperson;

import com.onclass.person.model.bootcampperson.BootcampPerson;
import com.onclass.person.model.bootcampperson.gateways.BootcampPersonRepositoryPort;
import com.onclass.person.r2dbc.entity.BootcampPersonEntity;
import com.onclass.person.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BootcampPersonRepositoryAdapter extends ReactiveAdapterOperations<
        BootcampPerson,
        BootcampPersonEntity,
        Long,
        BootcampPersonRepository
    > implements BootcampPersonRepositoryPort {

    public BootcampPersonRepositoryAdapter(BootcampPersonRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BootcampPerson.class));
    }

    @Override
    public Flux<BootcampPerson> findAllByPersonId(Long personId) {
        return repository.findAllByPersonId(personId)
                .map(super::toEntity);
    }

    @Override
    public Mono<BootcampPerson> save(BootcampPerson bootcampPerson) {
        return super.save(bootcampPerson);
    }
}
