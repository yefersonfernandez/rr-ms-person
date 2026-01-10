package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;

public class NotFoundException extends BusinessException {
    public NotFoundException(String message) {
        super(ExceptionStatusCode.NOT_FOUND, message, 404);
    }
}

