package com.onclass.person.r2dbc.person;

import com.onclass.person.model.person.Person;
import com.onclass.person.model.person.gateways.PersonRepositoryPort;
import com.onclass.person.r2dbc.entity.PersonEntity;
import com.onclass.person.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class PersonRepositoryAdapter extends ReactiveAdapterOperations<
        Person,
        PersonEntity,
        Long,
        PersonRepository
    > implements PersonRepositoryPort {

    public PersonRepositoryAdapter(PersonRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Person.class));
    }

    @Override
    public Mono<Person> savePerson(Person person) {
        return super.save(person);
    }

    @Override
    public Mono<Person> findPersonById(Long personId) {
        return repository.findById(personId)
                .map(super::toEntity);
    }
}
