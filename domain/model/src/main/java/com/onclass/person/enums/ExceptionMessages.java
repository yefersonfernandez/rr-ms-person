package com.onclass.person.enums;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    PERSON_NOT_FOUND("Person with ID %s not found."),
    ENROLLMENT_LIMIT_EXCEEDED("Enrollment limit of %s reached"),
    BOOTCAMP_COLLISION("Schedule collision with bootcamp %s");

    private final String message;

    ExceptionMessages(String message) {
        this.message = message;
    }

    public String format(Object... args) {
        return String.format(message, args);
    }
}
