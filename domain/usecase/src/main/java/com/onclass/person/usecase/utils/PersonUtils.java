package com.onclass.person.usecase.utils;

import com.onclass.person.model.bootcampperson.BootcampPerson;
import com.onclass.person.model.person.Person;
import com.onclass.person.port.sqs.model.PersonEnrollMessage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonUtils {
    public static PersonEnrollMessage buildPersonMessage(BootcampPerson bootcampPerson) {
        return PersonEnrollMessage.builder()
                .personId(bootcampPerson.getPersonId())
                .bootcampId(bootcampPerson.getBootcampId())
                .build();
    }
}
