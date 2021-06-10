package com.daema.rest.aspect;

import com.daema.rest.common.consts.PropertiesValue;
import com.daema.rest.common.enums.ServiceReturnMsgEnum;
import com.daema.rest.common.exception.CustomException;
import com.daema.rest.common.exception.DataNotFoundException;
import com.daema.rest.common.exception.ProcessErrorException;
import com.daema.rest.common.exception.SearchErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandlerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerAdvice.class);

    private final MessageSource messageSource;

    private final String STATUS = "status";
    private final String MESSAGE = "message";
    private final String ERROR = "error";

    public ErrorHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus httpErrorStatus, WebRequest request) {

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, httpErrorStatus.value());
        errorResponse.put(ERROR, httpErrorStatus.name());

        if(ex.getBindingResult().getFieldErrorCount() > 0){

            String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

            try{
                msg = messageSource.getMessage(msg, null, Locale.KOREA);
            }catch (NoSuchMessageException nme){
                nme.getMessage();
            }

            errorResponse.put(MESSAGE, msg);
        }else{
            errorResponse.put(MESSAGE, ServiceReturnMsgEnum.INVALID_ARGUMENT);
        }

        errorLogging(request, ex, null, httpErrorStatus);

        return new ResponseEntity<>(errorResponse, httpErrorStatus);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus httpErrorStatus, WebRequest request) {

        CustomException customException;

        if (("prod".equals(PropertiesValue.profilesActive)||"stag".equals(PropertiesValue.profilesActive) )) {
            customException = new CustomException(
                    httpErrorStatus.value(), "Exception occurred", httpErrorStatus);
        } else {
            customException = new CustomException(
                    httpErrorStatus.value(), ex.getMessage(), httpErrorStatus);
        }

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, customException.getStatus());
        errorResponse.put(MESSAGE, customException.getMessage());
        errorResponse.put(ERROR, customException.getHttpErrorStatus());

        errorLogging(request, ex, body, httpErrorStatus);

        return new ResponseEntity<>(errorResponse, httpErrorStatus);
    }

    // CustomException
    @ExceptionHandler({
            SearchErrorException.class
            , ProcessErrorException.class
            , DataNotFoundException.class
    })
    @ResponseBody
    public ResponseEntity<Object> handlerCustomException(Exception ex, WebRequest request) {

        CustomException customException = (CustomException) ex;

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put(STATUS, customException.getStatus());
        errorResponse.put(MESSAGE, customException.getMessage());
        errorResponse.put(ERROR, customException.getHttpErrorStatus());

        errorLogging(request, ex, null, customException.getHttpErrorStatus());

        return new ResponseEntity<>(errorResponse, customException.getHttpErrorStatus());
    }

    private void errorLogging(WebRequest request, Exception ex, Object body, HttpStatus httpErrorStatus) {

        //TODO parameter logging
        if (httpErrorStatus.is5xxServerError()) {
            logger.error("Exception occurred", ex.getMessage());
        } else {
            logger.warn("Exception occurred : " + ex.getMessage());
        }
    }
}
