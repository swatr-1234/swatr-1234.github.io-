package com.gslab.assignment.EmployeeManagement.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gslab.assignment.EmployeeManagement.entities.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest req) {
        ErrorDetails err = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdentityChangeException.class)
    ResponseEntity<?> handleIdentityChangeException(IdentityChangeException ex, WebRequest req) {
        ErrorDetails err = new ErrorDetails(new Date(), ex.getMessage(), req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleGlobalException(Exception ex, WebRequest req) {
        ErrorDetails err = new ErrorDetails(new Date(), ex.getLocalizedMessage(), req.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> customValidationErrorHandling(MethodArgumentNotValidException ex) {
        ErrorDetails err = new ErrorDetails(new Date(), "Validation Error", ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }


}
