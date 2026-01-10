package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;

public class AlreadyExistsException extends BusinessException {
    public AlreadyExistsException(String message) {
        super(ExceptionStatusCode.CONFLICT, message, 409);
    }
}
