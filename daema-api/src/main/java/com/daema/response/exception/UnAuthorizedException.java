package com.daema.response.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CustomException {

    private static final long serialVersionUID = 9082022281807093281L;

    public UnAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED.value(), message, HttpStatus.UNAUTHORIZED);
    }

    public UnAuthorizedException(int status, String message, HttpStatus httpErrorStatus) {
        super(status, message, httpErrorStatus);
    }
}
