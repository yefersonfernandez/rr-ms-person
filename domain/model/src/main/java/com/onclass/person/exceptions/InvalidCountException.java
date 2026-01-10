package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;

public class InvalidCountException extends BusinessException {
    public InvalidCountException(String message) {
        super(ExceptionStatusCode.BAD_REQUEST, message, 400);
    }
}

