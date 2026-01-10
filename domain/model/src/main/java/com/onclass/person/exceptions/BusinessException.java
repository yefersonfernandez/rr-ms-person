package com.onclass.person.exceptions;

import com.onclass.person.enums.ExceptionStatusCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ExceptionStatusCode statusCode;
    private final int status;

    public BusinessException(ExceptionStatusCode statusCode, String message, int status) {
        super(message);
        this.statusCode = statusCode;
        this.status = status;
    }
}