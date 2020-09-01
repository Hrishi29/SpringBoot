package org.example.exception;

public class EmployeeConflictException extends RuntimeException{
    public EmployeeConflictException() {
    }

    public EmployeeConflictException(String message) {
        super(message);
    }
}
