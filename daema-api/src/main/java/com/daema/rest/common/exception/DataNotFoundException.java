package com.daema.rest.common.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends CustomException {

    private static final long serialVersionUID = -6418431444176894850L;

    public DataNotFoundException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DataNotFoundException(int status, String message, HttpStatus httpErrorStatus) {
        super(status, message, httpErrorStatus);
    }
}
