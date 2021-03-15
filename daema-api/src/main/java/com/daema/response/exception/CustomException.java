package com.daema.response.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 7031287990911664640L;

    private int status;
    private String message;
    private HttpStatus httpErrorStatus;

    public CustomException() {
        super();
    }

    public CustomException(int status, String message, HttpStatus httpErrorStatus) {
        super(message);
        this.status = status;
        this.message = message;
        this.httpErrorStatus = httpErrorStatus;
    }
}
