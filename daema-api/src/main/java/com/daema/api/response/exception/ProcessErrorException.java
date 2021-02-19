package com.daema.api.response.exception;

import org.springframework.http.HttpStatus;

public class ProcessErrorException extends CustomException {

    private static final long serialVersionUID = 7131287990911664640L;

    public ProcessErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ProcessErrorException(int status, String message, HttpStatus httpErrorStatus) {
        super(status, message, httpErrorStatus);
    }
}
