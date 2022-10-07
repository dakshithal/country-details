package com.nordea.assignment.exception;

import com.nordea.assignment.model.ErrorMsg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorMsg> respondClientExceptions(HttpClientErrorException e) {
        ResponseEntity<ErrorMsg> response = new ResponseEntity(e.getStatusCode());
        switch (e.getStatusCode()) {
            case NOT_FOUND:
                return new ResponseEntity<>(new ErrorMsg("Country not found!"), HttpStatus.NOT_FOUND);
            case BAD_REQUEST:
                return new ResponseEntity<>(new ErrorMsg("Bad Request!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorMsg(e.getMessage()), e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsg> respondOtherExceptions(Exception e) {
        return new ResponseEntity<>(new ErrorMsg(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
