package org.example;

import org.example.exception.EmployeeConflictException;
import org.example.exception.EmployeeErrorClass;
import org.example.exception.EmployeeNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    ResponseEntity<EmployeeErrorClass> EmployeeNotFoundException(EmployeeNotFoundException ex){
        return new ResponseEntity<>(new EmployeeErrorClass(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<EmployeeErrorClass> EmployeeConflictException(EmployeeConflictException ex){
        return new ResponseEntity<>(new EmployeeErrorClass(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    ResponseEntity<EmployeeErrorClass> handleException(Exception ex){
        return new ResponseEntity<>(new EmployeeErrorClass(HttpStatus.BAD_REQUEST, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
