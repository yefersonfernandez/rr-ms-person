package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;

public class ExternalServiceException extends BusinessException {
    public ExternalServiceException(String message) {
        super(ExceptionStatusCode.INTERNAL_SERVER_ERROR, message, 500);    }
}
