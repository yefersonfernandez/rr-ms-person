package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;

public class BootcampCollisionException extends BusinessException {
    public BootcampCollisionException(String message) {
        super(ExceptionStatusCode.CONFLICT, message, 409);
    }
}
