package com.daema.rest.common.exception;

import org.springframework.http.HttpStatus;

public class SearchErrorException extends CustomException {

    private static final long serialVersionUID = 7131287990911664640L;

    public SearchErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public SearchErrorException(int status, String message, HttpStatus httpErrorStatus) {
        super(status, message, httpErrorStatus);
    }
}
