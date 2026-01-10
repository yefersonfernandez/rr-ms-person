package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;

public class EnrollmentLimitException extends BusinessException {
    public EnrollmentLimitException(String message) {
        super(ExceptionStatusCode.BAD_REQUEST, message, 400);
    }
}
