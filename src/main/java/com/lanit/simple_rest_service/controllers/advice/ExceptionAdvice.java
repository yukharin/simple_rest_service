package com.lanit.simple_rest_service.controllers.advice;

import com.google.common.base.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;


@ControllerAdvice
public class ExceptionAdvice {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> hendleExceptions(final Exception exception) {
        Throwable rootCause = Throwables.getRootCause(exception);
        if (rootCause instanceof ConstraintViolationException) {
            return new ResponseEntity<>(rootCause.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (rootCause instanceof EntityNotFoundException) {
            return new ResponseEntity<>(rootCause.getMessage(), HttpStatus.NOT_FOUND);
        } else if (rootCause instanceof EntityExistsException) {
            return new ResponseEntity<>(rootCause.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (rootCause instanceof ValidationException) {
            return new ResponseEntity<>(rootCause.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(rootCause.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
